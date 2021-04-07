package com.tilikki.training.unimager.demo.repositories

import android.util.Log
import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.util.*
import io.reactivex.Observable
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val unsplashApiInterface: UnsplashApiInterface,
    private val database: RoomDB
) :
    UnsplashRepository {
    override fun getPhotos(query: String): Observable<List<Photo>> {
        val result = unsplashApiInterface.getPhotos(query)
        return result.flatMap { response ->
            if (response.isSuccessful) {
                val bodyResult = response.body()?.results
                if (bodyResult != null) {
                    val fetchedPhotos = bodyResult.asDatabaseEntityPhotos()
                    database.photosDao.let { dao ->
                        dao.insertAll(fetchedPhotos)
                        dao.insertSearchResults(bodyResult.mapToSearchResults(query))
                    }
                    database.userDao.let { dao ->
                        bodyResult.map {
                            dao.insertUser(it.user.toDatabaseEntityUser())
                        }
                    }
                }
            }
            Observable.just(queryData(query))
        }.onErrorReturn {
            queryData(query)
        }
    }

    private fun queryData(query: String): List<Photo> {
        val fetch = database.photosDao.getPhotoSearchResult(query)
        Log.d(LogUtility.LOGGER_DATABASE_TAG, fetch.toString())
        return fetch.getPhotos().asDomainEntityPhotos()
    }

    override fun getPhotoDetail(photoId: String): Observable<PhotoDetail> {
        return unsplashApiInterface.getPhotoDetail(photoId).map {
            it.toDomainEntityPhotoDetail()
        }.onErrorReturn {
            val dbRes = database.photosDao.getPhotoDetailById(photoId)
            Log.e("owo", dbRes.toString())
            dbRes.getPhotoDetail()
        }
    }

    override fun getUserProfile(username: String): Observable<User> {
        return unsplashApiInterface.getUserProfile(username).map {
            it.toDomainEntityUser()
        }
    }

    override fun getUserPhotos(username: String): Observable<List<Photo>> {
        return unsplashApiInterface.getUserPhotos(username).map {
            it.asDomainEntityPhotos()
        }
    }
}
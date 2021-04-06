package com.tilikki.training.unimager.demo.repositories

import android.util.Log
import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.util.asDatabaseEntityPhotos
import com.tilikki.training.unimager.demo.util.asDomainEntityPhotos
import io.reactivex.Observable
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val unsplashApiInterface: UnsplashApiInterface,
    private val database: RoomDB
) :
    UnsplashRepository {
    override fun getPhotos(query: String): Observable<List<Photo>> {
        val result = unsplashApiInterface.getPhotos(query)
        return result.flatMap {
            if (it.isSuccessful) {
                val fetchedPhotos = it.body()?.results?.asDatabaseEntityPhotos(query)
                database.photosDao.let { dao ->
                    dao.deletePhotoResult(query)
                    if (fetchedPhotos != null) {
                        dao.insertAll(fetchedPhotos)
                    }
                }
            }
            Observable.just(queryData(query))
        }.onErrorReturn {
            queryData(query)
        }
    }

    private fun queryData(query: String): List<Photo> {
        val fetch = database.photosDao.getSearchResult(query)
        Log.d(LogUtility.LOGGER_DATABASE_TAG, fetch.toString())
        return fetch.asDomainEntityPhotos()
    }

    override fun getPhotoDetail(photoId: String): Observable<PhotoDetail> {
        return unsplashApiInterface.getPhotoDetail(photoId).map {
            it.toDomainEntityPhotoDetail()
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
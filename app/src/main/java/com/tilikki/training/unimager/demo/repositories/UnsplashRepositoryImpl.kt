package com.tilikki.training.unimager.demo.repositories

import android.util.Log
import com.tilikki.training.unimager.demo.database.EntityPhoto
import com.tilikki.training.unimager.demo.database.EntityUser
import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.network.model.BasicUrlResponse
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.util.asDatabaseEntityPhotos
import com.tilikki.training.unimager.demo.util.asDomainEntityPhotos
import com.tilikki.training.unimager.demo.util.getPhotos
import com.tilikki.training.unimager.demo.util.mapToSearchResults
import io.reactivex.Observable
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val unsplashApiInterface: UnsplashApiInterface,
    private val database: RoomDB
) :
    UnsplashRepository {
    override fun getPhotos(query: String): Observable<List<Photo>> {
        val result = unsplashApiInterface.getPhotos(query)
        return result.map { response ->
            if (response.isSuccessful) {
                val bodyResult = response.body()?.results
                if (bodyResult != null) {
                    val fetchedPhotos = bodyResult.asDatabaseEntityPhotos()
                    putFetchedPhotosIntoDB(fetchedPhotos, bodyResult, query)
                    return@map bodyResult.asDomainEntityPhotos()
                }
            }
            return@map queryPhotosFromDB(query)
        }.onErrorReturn {
            queryPhotosFromDB(query)
        }
    }

    private fun putFetchedPhotosIntoDB(
        fetchedPhotos: List<EntityPhoto>,
        bodyResult: List<NetworkPhoto>,
        query: String
    ) {
        database.run {
            photosDao.insertAll(fetchedPhotos)
            photosDao.insertSearchResults(bodyResult.mapToSearchResults(query))
            bodyResult.map {
                userDao.insertUser(it.user.toDatabaseEntityUser())
            }
        }
    }

    private fun queryPhotosFromDB(query: String): List<Photo> {
        val fetch = database.photosDao.getPhotoSearchResult(query)
        Log.d(LogUtility.LOGGER_DATABASE_TAG, fetch.toString())
        return fetch.getPhotos().asDomainEntityPhotos()
    }

    override fun getPhotoDetail(photoId: String): Observable<PhotoDetail> {
        return unsplashApiInterface.getPhotoDetail(photoId).map {
            it.toDomainEntityPhotoDetail()
        }.onErrorReturn {
            val dbRes = database.photosDao.getPhotoDetailById(photoId)
            Log.e(LogUtility.LOGGER_DATABASE_TAG, dbRes.toString())
            dbRes.getPhotoDetail()
        }
    }

    override fun getUserProfile(username: String): Observable<User> {
        return unsplashApiInterface.getUserProfile(username).map {
            database.userDao.insertUser(it.toDatabaseEntityUser())
            it.toDomainEntityUser()
        }.onErrorReturn {
            val res = getUserFromDB(username)
            Log.d(LogUtility.LOGGER_DATABASE_TAG, res.toString())
            res.toDomainEntityUser()
        }
    }

    private fun getUserFromDB(username: String): EntityUser {
        return database.userDao.getUserByUsername(username)
    }

    override fun getUserPhotos(username: String): Observable<List<Photo>> {
        return unsplashApiInterface.getUserPhotos(username).map {
            database.photosDao.insertAll(it.asDatabaseEntityPhotos())
            it.asDomainEntityPhotos()
        }.onErrorReturn {
            val res = getUserPhotosFromDB(username)
            Log.d(LogUtility.LOGGER_DATABASE_TAG, res.toString())
            res.asDomainEntityPhotos()
        }
    }

    override fun getRandomPhotosByTopic(topicId: String): Observable<List<Pair<Photo, User>>> {
        return unsplashApiInterface.getRandomPhotosByTopic(topicId).map { list ->
            list.map { Pair(it.toDomainEntityPhoto(), it.user.toDomainEntityUser()) }
        }
    }

    override fun downloadPhoto(url: String): Observable<BasicUrlResponse> {
        return unsplashApiInterface.downloadPhoto(url)
    }

    private fun getUserPhotosFromDB(username: String): List<EntityPhoto> {
        return database.userDao.getUserPhotosByUsername(username).photos
    }
}
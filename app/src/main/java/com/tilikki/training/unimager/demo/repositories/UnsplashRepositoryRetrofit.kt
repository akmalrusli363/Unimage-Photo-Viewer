package com.tilikki.training.unimager.demo.repositories

import android.util.Log
import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.network.model.NetworkUser
import com.tilikki.training.unimager.demo.util.NetworkUtilities
import com.tilikki.training.unimager.demo.util.asDatabaseEntityPhotos
import com.tilikki.training.unimager.demo.util.asDomainEntityPhotos
import io.reactivex.Observable
import javax.inject.Inject

class UnsplashRepositoryRetrofit @Inject constructor(
    private val unsplashApiInterface: UnsplashApiInterface,
    private val database: RoomDB
) :
    UnsplashRepository {
    override fun getPhotos(query: String): Observable<List<Photo>> {
        return if (NetworkUtilities.isInternetAvailable()) {
            val result = unsplashApiInterface.getPhotos(query)
            result.flatMap {
                val fetchedPhotos = it.results.asDatabaseEntityPhotos(query)
                database.photosDao.let { dao ->
                    dao.deletePhotoResult(query)
                    dao.insertAll(fetchedPhotos)
                    queryData(query)
                }
            }
        } else {
            queryData(query) ?: Observable.empty()
        }
    }

    private fun queryData(query: String): Observable<List<Photo>>? {
        val fetch = database.photosDao.getSearchResult(query)
        Log.d("UnsplashRoomDB", fetch.toString())
        return fetch.map { rxData ->
            rxData.asDomainEntityPhotos()
        }
    }

    override fun getUserProfile(query: String): Observable<NetworkUser> {
        return unsplashApiInterface.getUserProfile(query)
    }
}
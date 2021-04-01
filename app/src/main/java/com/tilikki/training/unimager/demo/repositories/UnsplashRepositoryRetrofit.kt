package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.network.model.NetworkUser
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
        return unsplashApiInterface.getPhotos(query).flatMap {
            val fetchedPhotos = it.results.asDatabaseEntityPhotos(query)
            database.photosDao.let { dao ->
                dao.deletePhotoResult(query)
                dao.insertAll(fetchedPhotos)
            }
            Observable.just(fetchedPhotos.asDomainEntityPhotos())
        }
    }

    override fun getUserProfile(query: String): Observable<NetworkUser> {
        return unsplashApiInterface.getUserProfile(query)
    }
}
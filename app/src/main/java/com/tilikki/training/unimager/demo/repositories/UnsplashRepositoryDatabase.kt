package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.database.RoomDatabase
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.model.NetworkUser
import com.tilikki.training.unimager.demo.util.asEntityPhotos
import io.reactivex.Observable

class UnsplashRepositoryDatabase(private val database: RoomDatabase) : UnsplashRepository {
    override fun getPhotos(query: String): Observable<List<Photo>> {
        val fetchedPhotos = database.photosDao.getPhotos().value
        return Observable.just(fetchedPhotos?.asEntityPhotos())
    }

    override fun getUserProfile(query: String): Observable<NetworkUser> {
        TODO("Not yet implemented")
    }
}
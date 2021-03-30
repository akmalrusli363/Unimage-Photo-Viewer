package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class UnsplashRepositoryRetrofit @Inject constructor(private val unsplashApiInterface: UnsplashApiInterface) :
    UnsplashRepository {
    override fun getPhotos(query: String): Observable<List<Photo>> {
        return unsplashApiInterface.getPhotos(query)
    }

    override fun getUserProfile(query: String): Observable<User> {
        return unsplashApiInterface.getUserProfile(query)
    }
}
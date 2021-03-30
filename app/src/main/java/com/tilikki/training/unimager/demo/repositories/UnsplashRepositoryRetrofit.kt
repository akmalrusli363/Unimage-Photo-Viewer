package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.repositories.response.PhotoList
import io.reactivex.Observable
import javax.inject.Inject

class UnsplashRepositoryRetrofit @Inject constructor(private val unsplashApiInterface: UnsplashApiInterface) :
    UnsplashRepository {
    override fun getPhotos(query: String): Observable<PhotoList> {
        return unsplashApiInterface.getPhotos(query)
    }

    override fun getUserProfile(query: String): Observable<User> {
        return unsplashApiInterface.getUserProfile(query)
    }
}
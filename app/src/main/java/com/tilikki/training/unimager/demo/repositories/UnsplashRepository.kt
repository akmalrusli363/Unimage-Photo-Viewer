package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.model.NetworkUser
import io.reactivex.Observable

interface UnsplashRepository {
    fun getPhotos(query: String): Observable<List<Photo>>
    fun getUserProfile(query: String): Observable<NetworkUser>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }
}


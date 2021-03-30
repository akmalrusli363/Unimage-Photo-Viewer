package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import io.reactivex.Observable

interface UnsplashRepository {
    fun getPhotos(query: String): Observable<List<Photo>>
    fun getUserProfile(query: String): Observable<User>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }
}


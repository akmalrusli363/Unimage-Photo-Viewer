package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.repositories.response.PhotoList
import io.reactivex.Observable

interface UnsplashRepository {
    fun getPhotos(query: String): Observable<PhotoList>
    fun getUserProfile(query: String): Observable<User>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }
}


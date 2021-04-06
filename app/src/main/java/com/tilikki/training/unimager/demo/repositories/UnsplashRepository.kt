package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import io.reactivex.Observable

interface UnsplashRepository {
    fun getPhotos(query: String): Observable<List<Photo>>
    fun getPhotoDetail(photoId: String): Observable<PhotoDetail>
    fun getUserProfile(username: String): Observable<User>
    fun getUserPhotos(username: String): Observable<List<Photo>>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }
}


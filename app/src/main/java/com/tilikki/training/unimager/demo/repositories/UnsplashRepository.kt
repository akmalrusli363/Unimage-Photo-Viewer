package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.model.BasicUrlResponse
import io.reactivex.Observable

interface UnsplashRepository {
    fun getPhotos(query: String): Observable<List<Photo>>
    fun getPhotoDetail(photoId: String): Observable<PhotoDetail>
    fun getUserProfile(username: String): Observable<User>
    fun getUserPhotos(username: String): Observable<List<Photo>>
    fun getRandomPhotosByTopic(topicId: String): Observable<List<Pair<Photo, User>>>

    fun downloadPhoto(url: String): Observable<BasicUrlResponse>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }
}


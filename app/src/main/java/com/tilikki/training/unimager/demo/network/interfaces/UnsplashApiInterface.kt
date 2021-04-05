package com.tilikki.training.unimager.demo.network.interfaces

import com.tilikki.training.unimager.demo.network.model.NetworkPhoto
import com.tilikki.training.unimager.demo.network.model.NetworkUser
import com.tilikki.training.unimager.demo.repositories.response.PhotoList
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Base URL: "https://api.unsplash.com/"
interface UnsplashApiInterface {
    @GET("/search/photos")
    fun getPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 20
    ): Observable<Response<PhotoList>>

    @GET("/users/{userName}")
    fun getUserProfile(@Path("userName") userName: String): Observable<NetworkUser>

    @GET("/photos/{photoId}")
    fun getPhotoDetail(@Path("photoId") photoId: String): Observable<NetworkPhoto>
}
package com.tilikki.training.unimager.demo.network.interfaces

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import io.reactivex.Observable
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
    ): Observable<List<Photo>>

    @GET("/users/{userName}")
    fun getUserProfile(@Path("userName") userName: String): Observable<User>
}
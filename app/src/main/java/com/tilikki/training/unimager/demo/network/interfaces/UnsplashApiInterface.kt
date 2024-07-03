package com.tilikki.training.unimager.demo.network.interfaces

import com.tilikki.training.unimager.demo.network.model.BasicUrlResponse
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto
import com.tilikki.training.unimager.demo.network.model.NetworkUser
import com.tilikki.training.unimager.demo.repositories.response.PhotoList
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

// Base URL: "https://api.unsplash.com/"
interface UnsplashApiInterface {
    @GET("/search/photos")
    fun getPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 50
    ): Observable<Response<PhotoList>>

    @GET("/search/photos")
    fun getPagingPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 50
    ): Single<PhotoList>

    @GET("/photos/random")
    fun getRandomPhotosByTopic(
        @Query("topics") topicId: String,
        @Query("count") count: Int = 10
    ): Observable<List<NetworkPhoto>>

    @GET("/users/{userName}")
    fun getUserProfile(@Path("userName") userName: String): Observable<NetworkUser>

    @GET("/users/{userName}/photos")
    fun getUserPhotos(@Path("userName") userName: String): Observable<List<NetworkPhoto>>

    @GET("/users/{userName}/photos")
    fun getUserPhotos(
        @Path("userName") userName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 50
    ): Single<List<NetworkPhoto>>

    @GET("/photos/{photoId}")
    fun getPhotoDetail(@Path("photoId") photoId: String): Observable<NetworkPhoto>

    @GET("/photos/{photoId}/download")
    fun downloadPhoto(
        @Path("photoId") photoId: String,
        @Query("ixid") ixid: String
    ): Observable<BasicUrlResponse>

    @GET
    fun downloadPhoto(@Url url: String): Observable<BasicUrlResponse> {
        val httpUrl = url.toHttpUrl()
        val ixid = httpUrl.queryParameter("ixid")
        if (httpUrl.encodedPath.contains("photos")) {
            val segments = httpUrl.pathSegments
            val photoIdIndex = segments.indexOf("photos") + 1
            val photoId = segments.getOrNull(photoIdIndex)
            if (!photoId.isNullOrBlank() && !ixid.isNullOrBlank()) {
                return downloadPhoto(photoId, ixid)
            }
        }
        return Observable.error(IllegalArgumentException("Invalid download URL link provided"))
    }
}

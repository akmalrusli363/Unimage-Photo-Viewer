package com.tilikki.training.unimager.demo.repositories

import androidx.paging.PagingData
import com.tilikki.training.unimager.demo.model.Photo
import io.reactivex.Flowable

interface UnsplashPagingRepository {
    fun getPhotos(query: String): Flowable<PagingData<Photo>>
}


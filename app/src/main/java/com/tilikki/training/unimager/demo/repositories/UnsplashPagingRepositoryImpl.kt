package com.tilikki.training.unimager.demo.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.repositories.response.PhotoList
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class UnsplashPagingRepositoryImpl @Inject constructor(
    private val unsplashApiInterface: UnsplashApiInterface,
    private val database: RoomDB
) : UnsplashPagingRepository {

    override fun getPhotos(query: String, page: Int): Flowable<PagingData<Photo>> {
        return buildFlowablePagingData {
            return@buildFlowablePagingData unsplashApiInterface.getPagingPhotos(query, page)
        }
    }

    private fun buildFlowablePagingData(
        dataSourceList: (page: Int) -> Single<PhotoList>
    ): Flowable<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 5,
            ),
            pagingSourceFactory = {
                BasePhotoPagingSource(dataSourceList)
            }
        ).flowable
    }
}
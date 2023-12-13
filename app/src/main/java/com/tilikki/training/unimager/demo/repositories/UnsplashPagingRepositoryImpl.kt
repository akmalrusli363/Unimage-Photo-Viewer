package com.tilikki.training.unimager.demo.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.repositories.response.PhotoList
import com.tilikki.training.unimager.demo.util.LogUtility
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class UnsplashPagingRepositoryImpl @Inject constructor(
    private val unsplashApiInterface: UnsplashApiInterface,
    private val database: RoomDB
) : UnsplashPagingRepository {

    override fun getPhotos(query: String): Flowable<PagingData<Photo>> {
        return buildFlowablePagingData { page ->
            Log.w(LogUtility.LOGGER_FETCH_TAG, "Searching $query on page $page...")
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
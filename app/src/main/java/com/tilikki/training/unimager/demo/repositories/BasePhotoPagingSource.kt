package com.tilikki.training.unimager.demo.repositories

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto
import com.tilikki.training.unimager.demo.repositories.response.PhotoList
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BasePhotoPagingSource(
    private val dataSourceList: (page: Int) -> Single<PhotoList>
) : RxPagingSource<Int, Photo>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Photo>> {
        val position = params.key ?: 1
        return dataSourceList(position).subscribeOn(Schedulers.io())
            .map { listResponse ->
                toLoadResult(listResponse, position)
            }
            .onErrorReturn { ex ->
                return@onErrorReturn LoadResult.Error(ex)
            }
    }

    private fun toLoadResult(
        response: PhotoList,
        position: Int
    ): LoadResult<Int, Photo> {
        return LoadResult.Page(
            data = response.results.map(NetworkPhoto::toDomainEntityPhoto),
            prevKey = when (position) {
                1 -> null
                else -> position - 1
            },
            nextKey = when {
                position >= response.total_pages -> null
                else -> position + 1
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
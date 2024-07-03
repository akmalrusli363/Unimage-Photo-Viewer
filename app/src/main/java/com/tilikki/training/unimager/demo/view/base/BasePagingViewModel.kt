package com.tilikki.training.unimager.demo.view.base

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.tilikki.training.unimager.demo.model.Photo
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow

abstract class BasePagingViewModel : BaseViewModel() {
    abstract fun fetchAsFlowable(): Flowable<PagingData<Photo>>

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchPhotoList(): Flow<PagingData<Photo>> {
        return fetchAsFlowable()
            .cachedIn(viewModelScope).asFlow()
    }
}
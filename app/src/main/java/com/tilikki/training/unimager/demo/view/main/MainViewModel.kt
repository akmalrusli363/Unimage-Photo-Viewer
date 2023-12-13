package com.tilikki.training.unimager.demo.view.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.repositories.UnsplashPagingRepository
import com.tilikki.training.unimager.demo.view.base.BaseViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.asFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(private val unsplashRepository: UnsplashPagingRepository) :
    BaseViewModel() {
    var searchQuery = MutableStateFlow("")
    var isSearching by mutableStateOf(false)

    fun triggerSearch(query: String) {
        this.searchQuery.value = query
        this.isSearching = true
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val photoListFlow: Flow<PagingData<Photo>> =
        searchQuery.flatMapLatest { query ->
            when {
                query.isNotBlank() -> fetchPhotos(query).cachedIn(viewModelScope).asFlow()
                else -> flowOf(PagingData.empty())
            }
        }

    fun fetchPhotos(query: String): Flowable<PagingData<Photo>> {
        return unsplashRepository.getPhotos(query)
    }
}
package com.tilikki.training.unimager.demo.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.training.unimager.demo.model.PageMetadata
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.view.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    BaseViewModel() {
    var searchQuery: String = ""

    private var _photos: MutableLiveData<List<Photo>> = MutableLiveData()
    val photos: LiveData<List<Photo>?>
        get() = _photos

    private var _pages: MutableLiveData<PageMetadata> = MutableLiveData()
    val pages: LiveData<PageMetadata>
        get() = _pages

    private var _updateFragment: MutableLiveData<Boolean> = MutableLiveData()
    val updateFragment: LiveData<Boolean>
        get() = _updateFragment

    fun fetchPhotos(query: String) {
        _updateFragment.postValue(true)
        fetchData(unsplashRepository.getPhotos(query),
            {
                _photos.postValue(it)
            }
        )
        _pages.postValue(object : PageMetadata(1) {
            override fun onEndOfDataAction() {
                addMorePhotos(searchQuery)
            }
        })
    }

    private fun addMorePhotos(query: String) {
        val page = (_pages.value?.page ?: 1)
        _updateFragment.postValue(false)
        fetchData(unsplashRepository.getPhotos(query, page),
            {
                val addedPhotos = (_photos.value ?: emptyList())
                    .toMutableList().apply {
                        addAll(it)
                    }
                _photos.postValue(addedPhotos)
            }
        )
    }
}
package com.tilikki.training.unimager.demo.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.view.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    BaseViewModel() {
    var searchQuery: String = ""
    var isSearching: MutableLiveData<Boolean> = MutableLiveData(false)

    private var _photos: MutableLiveData<List<Photo>> = MutableLiveData()
    val photos: LiveData<List<Photo>?>
        get() = _photos

    fun fetchPhotos(query: String) {
        isSearching.postValue(true)
        fetchData(unsplashRepository.getPhotos(query),
            {
                _photos.postValue(it)
            }
        )
    }
}
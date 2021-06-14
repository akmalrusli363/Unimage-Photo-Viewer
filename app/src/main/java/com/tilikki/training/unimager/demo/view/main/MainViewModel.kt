package com.tilikki.training.unimager.demo.view.main

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.view.base.BasePhotoGridViewModel
import io.reactivex.Observable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    BasePhotoGridViewModel() {

    private var searchQuery: String = ""

    fun fetchPhotos(query: String) {
        searchQuery = query
        fetchDataFromRepository(unsplashRepository.getPhotos(searchQuery)) {
            postPhotoList(it)
        }
    }

    override fun fetchMorePhotos(): Observable<List<Photo>> {
        return unsplashRepository.getPhotos(searchQuery, getPage())
    }
}

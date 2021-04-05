package com.tilikki.training.unimager.demo.view.photodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import javax.inject.Inject

class PhotoDetailViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {

    private val _photo: MutableLiveData<Photo> = MutableLiveData()
    val photo: LiveData<Photo>
        get() = _photo

}
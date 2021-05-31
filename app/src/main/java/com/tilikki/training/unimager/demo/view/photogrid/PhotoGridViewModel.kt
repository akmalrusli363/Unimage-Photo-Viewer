package com.tilikki.training.unimager.demo.view.photogrid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.model.Photo
import javax.inject.Inject

class PhotoGridViewModel @Inject constructor() : ViewModel() {
    private var _photos: MutableLiveData<List<Photo>> = MutableLiveData()
    val photos: LiveData<List<Photo>?>
        get() = _photos

    fun postPhotos(photos: List<Photo>?) {
        _photos.value = photos
    }
}
package com.tilikki.training.unimager.demo.view.photodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.view.base.BaseViewModel
import javax.inject.Inject

class PhotoDetailViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    BaseViewModel() {

    private val _photoId: MutableLiveData<String> = MutableLiveData()
    val photoId: LiveData<String>
        get() = _photoId

    private val _photo: MutableLiveData<PhotoDetail> = MutableLiveData()
    val photo: LiveData<PhotoDetail>
        get() = _photo

    fun attachPhoto(photoId: String?) {
        if (photoId != null) {
            setPhotoId(photoId)
        }
        fetchData(unsplashRepository.getPhotoDetail(_photoId.value!!),
            {
                _photo.postValue(it)
            }
        )
    }

    private fun setPhotoId(photoId: String) {
        _photoId.value = photoId
    }
}
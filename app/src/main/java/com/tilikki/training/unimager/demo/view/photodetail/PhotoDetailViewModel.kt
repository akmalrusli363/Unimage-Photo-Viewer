package com.tilikki.training.unimager.demo.view.photodetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.network.FetchResponse
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.util.LogUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PhotoDetailViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {

    private val _photoId: MutableLiveData<String> = MutableLiveData()
    val photoId: LiveData<String>
        get() = _photoId

    private val _photo: MutableLiveData<PhotoDetail> = MutableLiveData()
    val photo: LiveData<PhotoDetail>
        get() = _photo

    private var _successResponse: MutableLiveData<FetchResponse> = MutableLiveData()
    val successResponse: LiveData<FetchResponse>
        get() = _successResponse

    fun attachPhoto(photoId: String?) {
        if (photoId != null) {
            setPhotoId(photoId)
        }
        unsplashRepository.getPhotoDetail(_photoId.value!!).run {
            this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _photo.postValue(it)
                    Log.d(LogUtility.LOGGER_FETCH_TAG, it.toString())
                    setSuccessResponse(true, null)
                }, {
                    Log.e(LogUtility.LOGGER_FETCH_TAG, it.localizedMessage, it)
                    setSuccessResponse(false, it)
                })
        }
    }

    private fun setPhotoId(photoId: String) {
        _photoId.value = photoId
    }

    private fun setSuccessResponse(success: Boolean, error: Throwable?) {
        _successResponse.postValue(FetchResponse(success, error))
    }
}
package com.tilikki.training.unimager.demo.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.FetchResponse
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.util.LogUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {
    var searchQuery: String = ""

    private var _photos: MutableLiveData<List<Photo>> = MutableLiveData()
    val photos: LiveData<List<Photo>?>
        get() = _photos

    private var _successResponse: MutableLiveData<FetchResponse> = MutableLiveData()
    val successResponse: LiveData<FetchResponse>
        get() = _successResponse

    private var _isFetching: MutableLiveData<Boolean> = MutableLiveData()
    val isFetching: LiveData<Boolean>
        get() = _isFetching

    fun fetchPhotos(query: String) {
        unsplashRepository.getPhotos(query).run {
            _isFetching.postValue(true)
            this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _photos.postValue(it)
                    Log.d(LogUtility.LOGGER_FETCH_TAG, it.toString())
                    setSuccessResponse(true, null)
                    _isFetching.postValue(false)
                }, {
                    Log.e(LogUtility.LOGGER_FETCH_TAG, it.localizedMessage, it)
                    setSuccessResponse(false, it)
                    _isFetching.postValue(false)
                })
        }
    }

    private fun setSuccessResponse(success: Boolean, error: Throwable?) {
        _successResponse.postValue(FetchResponse(success, error))
    }
}
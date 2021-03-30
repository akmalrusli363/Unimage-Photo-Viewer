package com.tilikki.training.unimager.demo.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.FetchResponse
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {
    var searchQuery: String = ""

    private var _photos: MutableLiveData<List<Photo>> = MutableLiveData()
    val photos: LiveData<List<Photo>>
        get() = _photos

    private var _successResponse: MutableLiveData<FetchResponse> = MutableLiveData()
    val successResponse: LiveData<FetchResponse>
        get() = _successResponse

    fun fetchPhotos(query: String) {
        unsplashRepository.getPhotos(query).run {
            this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _photos.postValue(it.results)
                    Log.d("UnimageFetcher", it.results.toString())
                    setSuccessResponse(true, null)
                }, {
                    Log.e("UnimageFetcher", it.localizedMessage, it)
                    setSuccessResponse(false, it)
                })
        }
    }

    private fun setSuccessResponse(success: Boolean, error: Throwable?) {
        _successResponse.postValue(FetchResponse(success, error))
    }
}
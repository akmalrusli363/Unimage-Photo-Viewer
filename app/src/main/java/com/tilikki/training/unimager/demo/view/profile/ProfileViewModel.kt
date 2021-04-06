package com.tilikki.training.unimager.demo.view.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.FetchResponse
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.util.LogUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {

    private var _userProfile: MutableLiveData<User> = MutableLiveData()
    val userProfile: LiveData<User>
        get() = _userProfile

    private var _successResponse: MutableLiveData<FetchResponse> = MutableLiveData()
    val successResponse: LiveData<FetchResponse>
        get() = _successResponse

    fun fetchUserProfile(userId: String) {
        unsplashRepository.getUserProfile(userId).run {
            this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _userProfile.postValue(it.toDomainEntityUser())
                    Log.d(LogUtility.LOGGER_FETCH_TAG, it.toString())
                    setSuccessResponse(true, null)
                }, {
                    Log.e(LogUtility.LOGGER_FETCH_TAG, it.localizedMessage, it)
                    setSuccessResponse(false, it)
                })
        }
    }

    private fun setSuccessResponse(success: Boolean, error: Throwable?) {
        _successResponse.postValue(FetchResponse(success, error))
    }
}
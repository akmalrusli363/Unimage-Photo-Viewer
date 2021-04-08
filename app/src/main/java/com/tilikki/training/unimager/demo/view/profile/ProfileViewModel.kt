package com.tilikki.training.unimager.demo.view.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.FetchResponse
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.util.LogUtility
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {

    private var _userProfile: MutableLiveData<User> = MutableLiveData()
    val userProfile: LiveData<User>
        get() = _userProfile

    private var _userPhotos: MutableLiveData<List<Photo>> = MutableLiveData()
    val userPhotos: LiveData<List<Photo>?>
        get() = _userPhotos

    private var _successResponse: MutableLiveData<FetchResponse> = MutableLiveData()
    val successResponse: LiveData<FetchResponse>
        get() = _successResponse

    private var _isFetching: MutableLiveData<Boolean> = MutableLiveData()
    val isFetching: LiveData<Boolean>
        get() = _isFetching

    fun fetchUserProfile(username: String) {
        getObservableUserProfileAndPhotos(username).run {
            _isFetching.postValue(true)
            this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _userProfile.postValue(it.first)
                    _userPhotos.postValue(it.second)
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

    private fun getObservableUserProfileAndPhotos(username: String): Observable<Pair<User, List<Photo>>> {
        unsplashRepository.run {
            return Observable.zip(
                getUserProfile(username),
                getUserPhotos(username),
                { profile, photo ->
                    Pair(profile, photo)
                }
            )
        }
    }
}
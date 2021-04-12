package com.tilikki.training.unimager.demo.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.view.base.BaseViewModel
import io.reactivex.Observable
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    BaseViewModel() {

    private var _userProfile: MutableLiveData<User> = MutableLiveData()
    val userProfile: LiveData<User>
        get() = _userProfile

    private var _userPhotos: MutableLiveData<List<Photo>> = MutableLiveData()
    val userPhotos: LiveData<List<Photo>?>
        get() = _userPhotos

    fun fetchUserProfile(username: String) {
        fetchData(getObservableUserProfileAndPhotos(username),
            {
                _userProfile.postValue(it.first)
                _userPhotos.postValue(it.second)
            }
        )
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
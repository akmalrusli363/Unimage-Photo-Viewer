package com.tilikki.training.unimager.demo.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.view.base.BasePhotoGridViewModel
import io.reactivex.Observable
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    BasePhotoGridViewModel() {

    private var username: String = ""

    private var _userProfile: MutableLiveData<User> = MutableLiveData()
    val userProfile: LiveData<User>
        get() = _userProfile

    fun fetchUserProfile(username: String) {
        this.username = username
        fetchDataFromRepository(getObservableUserProfileAndPhotos(username)) {
            _userProfile.postValue(it.first)
            postPhotoList(it.second)
        }
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

    override fun fetchMorePhotos(): Observable<List<Photo>> {
        return unsplashRepository.getUserPhotos(username, getPage())
    }
}

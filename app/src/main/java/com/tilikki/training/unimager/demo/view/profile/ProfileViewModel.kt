package com.tilikki.training.unimager.demo.view.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.training.unimager.demo.model.PageMetadata
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.view.base.BaseViewModel
import io.reactivex.Observable
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    BaseViewModel() {

    private var username: String = ""

    private var _userProfile: MutableLiveData<User> = MutableLiveData()
    val userProfile: LiveData<User>
        get() = _userProfile

    private var _userPhotos: MutableLiveData<List<Photo>> = MutableLiveData()
    val userPhotos: LiveData<List<Photo>?>
        get() = _userPhotos

    private var _pages: MutableLiveData<PageMetadata> = MutableLiveData()
    val pages: LiveData<PageMetadata>
        get() = _pages

    private var _updateFragment: MutableLiveData<Boolean> = MutableLiveData()
    val updateFragment: LiveData<Boolean>
        get() = _updateFragment

    private var lastFetchedData: Int = -1

    fun fetchUserProfile(username: String) {
        this.username = username
        _updateFragment.postValue(true)
        fetchData(getObservableUserProfileAndPhotos(username),
            {
                _userProfile.postValue(it.first)
                _userPhotos.postValue(it.second)
            }
        )
        _pages.postValue(object : PageMetadata(1) {
            override fun onEndOfDataAction() {
                Log.w("osdfko", "continue...")
                addMorePhotos(username)
            }
        })
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

    private fun addMorePhotos(username: String) {
        val page = (_pages.value?.page ?: 1)
        _updateFragment.postValue(false)
        fetchData(unsplashRepository.getUserPhotos(username, page),
            {
                val addedPhotos = (_userPhotos.value ?: emptyList())
                    .toMutableList().apply {
                        if (!containsAll(it)) {
                            lastFetchedData = it.size
                            addAll(it)
                        } else {
                            Log.e("uwu", "already!")
                        }
                    }
                _userPhotos.postValue(addedPhotos)
            }
        )
    }
}

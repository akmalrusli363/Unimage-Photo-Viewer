package com.tilikki.training.unimager.demo.view.photodetail

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.model.PhotoTopicData
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.util.downloadFile
import com.tilikki.training.unimager.demo.view.base.BaseViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject

class PhotoDetailViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    BaseViewModel() {

    private val _photoId: MutableLiveData<String> = MutableLiveData()
    val photoId: LiveData<String>
        get() = _photoId

    private val _photo: MutableLiveData<PhotoDetail> = MutableLiveData()
    val photo: LiveData<PhotoDetail>
        get() = _photo

    private val _featuredPhotos: MutableLiveData<List<Pair<Photo, User>>> = MutableLiveData()
    val featuredPhotos: LiveData<List<Pair<Photo, User>>>
        get() = _featuredPhotos

    fun attachPhoto(photoId: String?) {
        if (photoId != null) {
            setPhotoId(photoId)
        }
        fetchData(unsplashRepository.getPhotoDetail(_photoId.value!!),
            {
                _photo.postValue(it)
                fetchFeaturedPhotos(it.topics.randomOrNull())
            }
        )
    }

    fun fetchFeaturedPhotos(topic: PhotoTopicData? = null) {
        // TODO to be filled by topics
        fetchData(unsplashRepository.getRandomPhotosByTopic(topic?.id.orEmpty()), {
            _featuredPhotos.postValue(it)
        }, { ex ->
            if (ex is SocketTimeoutException) {
                _featuredPhotos.postValue(listOf())
            }
        })
    }

    private fun setPhotoId(photoId: String) {
        _photoId.value = photoId
    }

    fun downloadPhoto(context: Context) {
        _photo.value?.let { photo ->
            fetchData(unsplashRepository.downloadPhoto(photo.downloadUrl), onSuccess = {
                context.downloadFile(Uri.parse(it.link), photo.id)
            }, onFail = {})
        }
    }
}

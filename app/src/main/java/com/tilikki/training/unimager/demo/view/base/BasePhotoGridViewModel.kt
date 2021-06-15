package com.tilikki.training.unimager.demo.view.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.training.unimager.demo.model.PageMetadata
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.util.LogUtility
import io.reactivex.Observable

abstract class BasePhotoGridViewModel : BaseViewModel() {
    private var _photoList: MutableLiveData<List<Photo>> = MutableLiveData()
    val photoList: LiveData<List<Photo>?>
        get() = _photoList

    private var _updateFragment: MutableLiveData<Boolean> = MutableLiveData()
    val updateFragment: LiveData<Boolean>
        get() = _updateFragment

    private var _pages: MutableLiveData<PageMetadata> = MutableLiveData()
    val pages: LiveData<PageMetadata>
        get() = _pages

    private var lastFetchedData: Int = -1

    fun getPage(): Int {
        return _pages.value!!.page
    }

    fun addMorePhotos() {
        _pages.value!!.addPage()
        _updateFragment.postValue(false)
        if (successResponse.value?.success == false) {
            Log.i(LogUtility.LOGGER_FETCH_TAG, "End of data!")
            return
        }
        if (lastFetchedData != PageMetadata.MAX_ITEMS_PER_PAGE) {
            Log.i(LogUtility.LOGGER_FETCH_TAG, "End of data!")
            return
        }
        fetchData(
            fetchMorePhotos(),
            {
                val addedPhotos = (_photoList.value ?: emptyList())
                    .toMutableList().apply {
                        lastFetchedData = it.size
                        addAll(it)
                    }
                _photoList.postValue(addedPhotos.distinct())
            }
        )
    }

    protected abstract fun fetchMorePhotos(): Observable<List<Photo>>

    protected fun <T> fetchDataFromRepository(
        observable: Observable<T>,
        onSuccess: (T) -> Unit
    ) {
        _updateFragment.postValue(true)
        fetchData(observable, onSuccess)
        _pages.postValue(object : PageMetadata(1) {
            override fun onEndOfDataAction() {
                addMorePhotos()
            }
        })
    }

    protected fun postPhotoList(photoList: List<Photo>) {
        lastFetchedData = photoList.size
        _photoList.postValue(photoList)
    }
}

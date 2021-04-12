package com.tilikki.training.unimager.demo.view.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.network.FetchResponse
import com.tilikki.training.unimager.demo.util.LogUtility
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

    private var _successResponse: MutableLiveData<FetchResponse> = MutableLiveData()
    val successResponse: LiveData<FetchResponse>
        get() = _successResponse

    private var _isFetching: MutableLiveData<Boolean> = MutableLiveData()
    val isFetching: LiveData<Boolean>
        get() = _isFetching

    protected fun <T> fetchData(
        observable: Observable<T>,
        onSuccess: (T) -> Unit,
        onFail: (Throwable) -> Unit = {}
    ) {
        observable.run {
            _isFetching.postValue(true)
            this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(LogUtility.LOGGER_FETCH_TAG, it.toString())
                    onSuccess(it)
                    setSuccessResponse(true, null)
                    _isFetching.postValue(false)
                }, {
                    Log.e(LogUtility.LOGGER_FETCH_TAG, it.localizedMessage, it)
                    onFail(it)
                    setSuccessResponse(false, it)
                    _isFetching.postValue(false)
                })
        }
    }

    private fun setSuccessResponse(success: Boolean, error: Throwable?) {
        _successResponse.postValue(FetchResponse(success, error))
    }
}
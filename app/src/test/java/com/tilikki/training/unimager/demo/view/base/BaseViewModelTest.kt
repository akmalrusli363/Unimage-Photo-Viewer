package com.tilikki.training.unimager.demo.view.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tilikki.training.unimager.demo.network.FetchResponse
import com.tilikki.training.unimager.demo.testUtility.RxSchedulerRule
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BaseViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private val mockBaseViewModel = BaseViewModelMock()

    @Mock
    private lateinit var fetchStatusObserver: Observer<FetchResponse>

    @Mock
    private lateinit var fetchObserver: Observer<Boolean>

    private open class BaseViewModelMock : BaseViewModel() {
        public override fun <T> fetchData(
            observable: Observable<T>,
            onSuccess: (T) -> Unit,
            onFail: (Throwable) -> Unit
        ) {
            super.fetchData(observable, onSuccess, onFail)
        }
    }

    @Test
    fun fetchData_runSuccessfully_success() {
        val observable: Observable<String> = Observable.just("Hello")
        mockBaseViewModel.run {
            fetchData(observable, {}, {})

            isFetching.observeForever(fetchObserver)
            successResponse.observeForever(fetchStatusObserver)

            Assert.assertFalse(isFetching.value!!)
            successResponse.value!!.let {
                Assert.assertTrue(it.success)
                Assert.assertEquals(it.error, null)
            }
        }
    }

    @Test
    fun fetchData_runError_success() {
        val myError = NullPointerException()
        val observable: Observable<Exception> = Observable.error(myError)
        mockBaseViewModel.run {
            fetchData(observable, {}, {})

            isFetching.observeForever(fetchObserver)
            successResponse.observeForever(fetchStatusObserver)

            Assert.assertFalse(isFetching.value!!)
            successResponse.value!!.let {
                Assert.assertFalse(it.success)
                Assert.assertEquals(it.error, myError)
            }
        }
    }

    @Test
    fun fetchData_runAbruptly_success() {
        val observable: Observable<Array<Int>> = Observable.just(arrayOf(1, 2))
        mockBaseViewModel.run {
            fetchData(observable, { it[2] }, {})

            isFetching.observeForever(fetchObserver)
            successResponse.observeForever(fetchStatusObserver)

            Assert.assertFalse(isFetching.value!!)
            successResponse.value!!.let {
                Assert.assertFalse(it.success)
            }
        }
    }
}
package com.tilikki.training.unimager.demo.view.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.testUtility.RxSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

/**
 * Must be run using `@RunWith(MockitoJUnitRunner::class)`!
 */
abstract class GenericViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    protected val unsplashRepository: UnsplashRepository =
        Mockito.mock(UnsplashRepository::class.java)

    @Before
    abstract fun setup()
}
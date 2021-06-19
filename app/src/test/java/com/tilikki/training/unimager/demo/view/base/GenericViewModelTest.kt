package com.tilikki.training.unimager.demo.view.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.testUtility.ClassAnnotationChecker
import com.tilikki.training.unimager.demo.testUtility.RxSchedulerRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

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

    @Before
    fun verifyClassAnnotations() {
        val classAnnotationChecker =
            ClassAnnotationChecker(GenericViewModelTest::class.java, this.javaClass)
        Assert.assertTrue(classAnnotationChecker.isSubclass())
        if (!classAnnotationChecker.hasRunWithAnnotation()) {
            throw MockitoException("The inherited class doesn't contains @RunWith(MockitoJUnitRunner::class) annotation")
        }
        if (!classAnnotationChecker.hasRunWithAnnotationParameter(MockitoJUnitRunner::class)) {
            throw MockitoException("The inherited class doesn't run with MockitoJUnitRunner within @RunWith annotation")
        }
    }
}
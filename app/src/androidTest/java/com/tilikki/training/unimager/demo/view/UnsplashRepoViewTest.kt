package com.tilikki.training.unimager.demo.view

import com.tilikki.training.unimager.demo.injector.singleton.FakeRepositorySingleton
import org.junit.After
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Spy

abstract class UnsplashRepoViewTest : ViewTest {
    @Spy
    protected val fakeRepository = FakeRepositorySingleton.fakeUnsplashRepository

    @Before
    fun init() {
        Mockito.reset(fakeRepository)
    }

    @After
    fun teardown() {
        Mockito.verifyNoMoreInteractions(fakeRepository)
    }
}
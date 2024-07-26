package com.tilikki.training.unimager.demo.view

import com.tilikki.training.unimager.demo.injector.singleton.FakeRepositorySingleton
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Spy

abstract class UnsplashRepoViewTest : ComposeViewTest() {
    @Spy
    protected val fakeRepository = FakeRepositorySingleton.fakeUnsplashRepository

    @get:Rule
    val skipTeardownIfFailedRule = SkipTeardownIfFailedRule()

    @Before
    fun init() {
        Mockito.reset(fakeRepository)
    }

    @After
    fun teardown() {
        if (skipTeardownIfFailedRule.isTestSuccess()) {
            Mockito.verifyNoMoreInteractions(fakeRepository)
        }
    }
}
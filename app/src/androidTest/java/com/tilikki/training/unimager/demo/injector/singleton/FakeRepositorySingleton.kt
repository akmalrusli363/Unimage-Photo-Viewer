package com.tilikki.training.unimager.demo.injector.singleton

import com.tilikki.training.unimager.demo.repositories.FakeRepositoryStatus
import com.tilikki.training.unimager.demo.repositories.FakeUnsplashRepository
import org.mockito.Mockito

object FakeRepositorySingleton {
    val fakeUnsplashRepository: FakeUnsplashRepository =
        Mockito.spy(FakeUnsplashRepository::class.java)

    val emptyFakeRepository = FakeUnsplashRepository().apply {
        setRepositoryStatus(FakeRepositoryStatus.FETCH_EMPTY)
    }

    val errorFakeRepository = FakeUnsplashRepository().apply {
        setRepositoryStatus(FakeRepositoryStatus.FETCH_ERROR)
    }
}
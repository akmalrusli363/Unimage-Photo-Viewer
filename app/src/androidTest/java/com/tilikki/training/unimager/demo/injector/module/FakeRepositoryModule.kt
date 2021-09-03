package com.tilikki.training.unimager.demo.injector.module

import com.tilikki.training.unimager.demo.datasets.TestDataConstants.DEMO_SEARCH_EMPTY
import com.tilikki.training.unimager.demo.datasets.TestDataConstants.DEMO_SEARCH_ERROR
import com.tilikki.training.unimager.demo.datasets.TestDataConstants.DEMO_USERNAME
import com.tilikki.training.unimager.demo.datasets.TestDataConstants.DEMO_USERNAME_ERROR
import com.tilikki.training.unimager.demo.datasets.TestDataConstants.DEMO_USERNAME_NO_PHOTO
import com.tilikki.training.unimager.demo.repositories.FakeRepositoryStatus
import com.tilikki.training.unimager.demo.repositories.FakeUnsplashRepository
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import dagger.Module
import dagger.Provides
import org.mockito.Mockito

@Module
class FakeRepositoryModule {
    private val emptyFakeRepository = FakeUnsplashRepository().apply {
        setRepositoryStatus(FakeRepositoryStatus.FETCH_EMPTY)
    }
    private val errorFakeRepository = FakeUnsplashRepository().apply {
        setRepositoryStatus(FakeRepositoryStatus.FETCH_ERROR)
    }

    @Provides
    fun provideUnsplashRepositoryImpl(): UnsplashRepository {
        val fakeRepository = Mockito.spy(FakeUnsplashRepository::class.java)
        Mockito.doReturn(emptyFakeRepository.getPhotos(DEMO_SEARCH_EMPTY))
            .`when`(fakeRepository).getPhotos(DEMO_SEARCH_EMPTY)
        Mockito.doReturn(errorFakeRepository.getPhotos(DEMO_SEARCH_ERROR))
            .`when`(fakeRepository).getPhotos(DEMO_SEARCH_ERROR)
        Mockito.doReturn(fakeRepository.getUserPhotos(DEMO_USERNAME, 15))
            .`when`(fakeRepository).getUserPhotos(DEMO_USERNAME)
        Mockito.doReturn(fakeRepository.getUserPhotos(DEMO_USERNAME_NO_PHOTO, 0))
            .`when`(fakeRepository).getUserPhotos(DEMO_USERNAME_NO_PHOTO)
        Mockito.doReturn(errorFakeRepository.getUserPhotos(DEMO_USERNAME_ERROR))
            .`when`(fakeRepository).getUserPhotos(DEMO_USERNAME_ERROR)
        return fakeRepository
    }
}

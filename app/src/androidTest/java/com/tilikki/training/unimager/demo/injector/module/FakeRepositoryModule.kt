package com.tilikki.training.unimager.demo.injector.module

import com.tilikki.training.unimager.demo.datasets.TestDataConstants.DEMO_SEARCH_ERROR
import com.tilikki.training.unimager.demo.repositories.FakeUnsplashRepository
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import dagger.Module
import dagger.Provides
import org.mockito.Mockito

@Module
class FakeRepositoryModule {
    private val errorFakeRepository = FakeUnsplashRepository().apply {
        setReturnError(true)
    }

    @Provides
    fun provideUnsplashRepositoryImpl(): UnsplashRepository {
        val fakeRepository = Mockito.spy(FakeUnsplashRepository::class.java)
        Mockito.doReturn(errorFakeRepository.getPhotos(DEMO_SEARCH_ERROR))
            .`when`(fakeRepository).getPhotos(DEMO_SEARCH_ERROR)
        return fakeRepository
    }
}

package com.tilikki.training.unimager.demo.injector.module

import com.tilikki.training.unimager.demo.injector.singleton.FakeRepositorySingleton
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import dagger.Module
import dagger.Provides

@Module
class FakeRepositoryModule {


    @Provides
    fun provideUnsplashRepositoryImpl(): UnsplashRepository {
        return FakeRepositorySingleton.fakeUnsplashRepository
    }
}

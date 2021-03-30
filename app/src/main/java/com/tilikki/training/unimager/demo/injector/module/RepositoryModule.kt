package com.tilikki.training.unimager.demo.injector.module

import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.repositories.UnsplashRepositoryRetrofit
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideUnsplashRepositoryRetrofit(unsplashRepositoryRetrofit: UnsplashRepositoryRetrofit): UnsplashRepository
}
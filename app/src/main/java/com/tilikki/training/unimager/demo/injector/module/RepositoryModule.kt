package com.tilikki.training.unimager.demo.injector.module

import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import com.tilikki.training.unimager.demo.repositories.UnsplashRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideUnsplashRepositoryImpl(
        unsplashRepositoryImpl: UnsplashRepositoryImpl
    ): UnsplashRepository
}
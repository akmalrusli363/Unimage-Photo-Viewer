package com.tilikki.training.unimager.demo.injector.module

import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [RepositoryModule::class])
class UnsplashModule {
    @Provides
    fun providesUnsplashInterface(@Reactive reactiveRetrofit: Retrofit): UnsplashApiInterface {
        return reactiveRetrofit.create(UnsplashApiInterface::class.java)
    }
}

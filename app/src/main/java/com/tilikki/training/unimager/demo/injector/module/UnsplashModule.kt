package com.tilikki.training.unimager.demo.injector.module

import android.app.Application
import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [RepositoryModule::class])
class UnsplashModule {
    @Provides
    fun provideDatabase(application: Application): RoomDB {
        return RoomDB.getDatabase(application)
    }

    @Provides
    fun providesUnsplashInterface(@Reactive reactiveRetrofit: Retrofit): UnsplashApiInterface {
        return reactiveRetrofit.create(UnsplashApiInterface::class.java)
    }
}

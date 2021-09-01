package com.tilikki.training.unimager.demo.injector.module

import android.app.Application
import com.tilikki.training.unimager.demo.database.RoomDB
import dagger.Module
import dagger.Provides

@Module(includes = [FakeRepositoryModule::class])
class FakeUnsplashModule {

    @Provides
    fun provideDatabase(application: Application): RoomDB {
        return RoomDB.getDatabase(application)
    }

}

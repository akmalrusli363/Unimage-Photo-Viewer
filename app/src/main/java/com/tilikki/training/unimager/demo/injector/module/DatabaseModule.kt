package com.tilikki.training.unimager.demo.injector.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application
    }
}
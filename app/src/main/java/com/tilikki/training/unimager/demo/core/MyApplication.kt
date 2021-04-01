package com.tilikki.training.unimager.demo.core

import android.app.Application
import com.tilikki.training.unimager.demo.injector.component.AppComponent
import com.tilikki.training.unimager.demo.injector.component.DaggerAppComponent
import com.tilikki.training.unimager.demo.injector.component.DaggerMainActivityComponent
import com.tilikki.training.unimager.demo.injector.component.MainActivityComponent
import com.tilikki.training.unimager.demo.injector.module.AppModule
import com.tilikki.training.unimager.demo.injector.module.NetModule
import com.tilikki.training.unimager.demo.injector.module.UnsplashModule
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository

class MyApplication : Application() {
    private lateinit var appComponent: AppComponent
    private lateinit var mainActivityComponent: MainActivityComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule(UnsplashRepository.BASE_URL))
            .build()

        mainActivityComponent = DaggerMainActivityComponent.builder()
            .appComponent(appComponent)
            .unsplashModule(UnsplashModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    fun getMainActivityComponent(): MainActivityComponent {
        return mainActivityComponent
    }
}
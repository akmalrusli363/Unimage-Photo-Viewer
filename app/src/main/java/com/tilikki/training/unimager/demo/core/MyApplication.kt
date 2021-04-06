package com.tilikki.training.unimager.demo.core

import android.app.Application
import com.tilikki.training.unimager.demo.injector.component.AppComponent
import com.tilikki.training.unimager.demo.injector.component.DaggerAppComponent
import com.tilikki.training.unimager.demo.injector.component.DaggerUserComponent
import com.tilikki.training.unimager.demo.injector.component.UserComponent
import com.tilikki.training.unimager.demo.injector.module.AppModule
import com.tilikki.training.unimager.demo.injector.module.NetModule
import com.tilikki.training.unimager.demo.injector.module.UnsplashModule
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository

class MyApplication : Application() {
    private lateinit var appComponent: AppComponent
    private lateinit var userComponent: UserComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule(UnsplashRepository.BASE_URL))
            .build()

        userComponent = DaggerUserComponent.builder()
            .appComponent(appComponent)
            .unsplashModule(UnsplashModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    fun getUserComponent(): UserComponent {
        return userComponent
    }
}
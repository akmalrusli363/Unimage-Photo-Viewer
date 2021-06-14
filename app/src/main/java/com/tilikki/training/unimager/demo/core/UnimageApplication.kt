package com.tilikki.training.unimager.demo.core

import com.tilikki.training.unimager.demo.injector.component.DaggerAppComponent
import com.tilikki.training.unimager.demo.repositories.UnsplashRepository
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class UnimageApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .baseUrl(UnsplashRepository.BASE_URL)
            .build()
    }
}

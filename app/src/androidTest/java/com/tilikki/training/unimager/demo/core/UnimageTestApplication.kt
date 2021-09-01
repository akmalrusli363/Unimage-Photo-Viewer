package com.tilikki.training.unimager.demo.core

import com.tilikki.training.unimager.demo.injector.component.DaggerAppTestComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class UnimageTestApplication : UnimageApplication() {
    val component = DaggerAppTestComponent.builder()
        .application(this)
        .build()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }
}

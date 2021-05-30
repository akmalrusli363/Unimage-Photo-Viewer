package com.tilikki.training.unimager.demo.view.main

import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.injector.module.UnsplashModule
import dagger.Module
import dagger.Provides

@Module(includes = [UnsplashModule::class])
class MainActivityModule {
    @Provides
    fun provideViewModel(viewModel: MainViewModel): ViewModel {
        return viewModel
    }
}
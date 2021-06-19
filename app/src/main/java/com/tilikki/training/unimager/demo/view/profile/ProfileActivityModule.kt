package com.tilikki.training.unimager.demo.view.profile

import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.injector.module.UnsplashModule
import dagger.Module
import dagger.Provides

@Module(includes = [UnsplashModule::class])
class ProfileActivityModule {
    @Provides
    fun provideViewModel(viewModel: ProfileViewModel): ViewModel {
        return viewModel
    }
}
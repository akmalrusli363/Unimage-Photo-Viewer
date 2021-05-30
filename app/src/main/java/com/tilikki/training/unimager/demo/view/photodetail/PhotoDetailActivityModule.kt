package com.tilikki.training.unimager.demo.view.photodetail

import androidx.lifecycle.ViewModel
import com.tilikki.training.unimager.demo.injector.module.UnsplashModule
import dagger.Module
import dagger.Provides

@Module(includes = [UnsplashModule::class])
class PhotoDetailActivityModule {
    @Provides
    fun provideViewModel(viewModel: PhotoDetailViewModel): ViewModel {
        return viewModel
    }
}
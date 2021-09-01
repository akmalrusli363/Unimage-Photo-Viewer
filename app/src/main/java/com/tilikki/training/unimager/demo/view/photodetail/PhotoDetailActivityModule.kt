package com.tilikki.training.unimager.demo.view.photodetail

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides

@Module
class PhotoDetailActivityModule {
    @Provides
    fun provideViewModel(viewModel: PhotoDetailViewModel): ViewModel {
        return viewModel
    }
}

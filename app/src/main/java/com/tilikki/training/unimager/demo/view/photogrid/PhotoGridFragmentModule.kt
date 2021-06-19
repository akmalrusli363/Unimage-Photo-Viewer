package com.tilikki.training.unimager.demo.view.photogrid

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides

@Module
class PhotoGridFragmentModule {
    @Provides
    fun provideViewModel(viewModel: PhotoGridViewModel): ViewModel {
        return viewModel
    }
}
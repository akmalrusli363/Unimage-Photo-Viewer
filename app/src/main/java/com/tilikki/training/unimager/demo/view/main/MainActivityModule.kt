package com.tilikki.training.unimager.demo.view.main

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun provideViewModel(viewModel: MainViewModel): ViewModel {
        return viewModel
    }
}

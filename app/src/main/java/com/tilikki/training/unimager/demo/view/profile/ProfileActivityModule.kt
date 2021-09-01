package com.tilikki.training.unimager.demo.view.profile

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides

@Module
class ProfileActivityModule {
    @Provides
    fun provideViewModel(viewModel: ProfileViewModel): ViewModel {
        return viewModel
    }
}

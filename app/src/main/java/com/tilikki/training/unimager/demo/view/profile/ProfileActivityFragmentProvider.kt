package com.tilikki.training.unimager.demo.view.profile

import com.tilikki.training.unimager.demo.view.photogrid.PhotoGridFragment
import com.tilikki.training.unimager.demo.view.photogrid.PhotoGridFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileActivityFragmentProvider {
    @ContributesAndroidInjector(modules = [PhotoGridFragmentModule::class])
    abstract fun providePhotoGridFragment(): PhotoGridFragment
}
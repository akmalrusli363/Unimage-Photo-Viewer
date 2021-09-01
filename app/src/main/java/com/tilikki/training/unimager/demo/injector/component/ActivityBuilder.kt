package com.tilikki.training.unimager.demo.injector.component

import com.tilikki.training.unimager.demo.view.main.MainActivity
import com.tilikki.training.unimager.demo.view.main.MainActivityFragmentProvider
import com.tilikki.training.unimager.demo.view.main.MainActivityModule
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivity
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivityModule
import com.tilikki.training.unimager.demo.view.profile.ProfileActivity
import com.tilikki.training.unimager.demo.view.profile.ProfileActivityFragmentProvider
import com.tilikki.training.unimager.demo.view.profile.ProfileActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainActivityFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [PhotoDetailActivityModule::class])
    abstract fun bindPhotoDetailActivity(): PhotoDetailActivity

    @ContributesAndroidInjector(modules = [ProfileActivityModule::class, ProfileActivityFragmentProvider::class])
    abstract fun bindProfileActivity(): ProfileActivity
}

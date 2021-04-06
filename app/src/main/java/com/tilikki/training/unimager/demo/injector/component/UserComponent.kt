package com.tilikki.training.unimager.demo.injector.component

import com.tilikki.training.unimager.demo.injector.module.UnsplashModule
import com.tilikki.training.unimager.demo.injector.module.ViewModelModule
import com.tilikki.training.unimager.demo.injector.scope.MainActivityScope
import com.tilikki.training.unimager.demo.view.main.MainActivity
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivity
import com.tilikki.training.unimager.demo.view.profile.ProfileActivity
import dagger.Component

@MainActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [UnsplashModule::class, ViewModelModule::class]
)
interface UserComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: PhotoDetailActivity)
    fun inject(activity: ProfileActivity)
}
package com.tilikki.training.unimager.demo.injector.component

import android.app.Application
import com.tilikki.training.unimager.demo.core.UnimageApplication
import com.tilikki.training.unimager.demo.injector.module.FakeUnsplashModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, FakeUnsplashModule::class, ActivityBuilder::class, FragmentBuilder::class])
interface AppTestComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppTestComponent
    }

    override fun inject(instance: DaggerApplication)

    fun inject(app: UnimageApplication)

}

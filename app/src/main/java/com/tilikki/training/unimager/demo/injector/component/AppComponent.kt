package com.tilikki.training.unimager.demo.injector.component

import android.app.Application
import com.tilikki.training.unimager.demo.core.UnimageApplication
import com.tilikki.training.unimager.demo.injector.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, NetModule::class, ActivityBuilder::class, UnsplashModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun baseUrl(baseUrl: String): Builder
        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)

    fun inject(app: UnimageApplication)

    @Callback
    fun retrofit(): Retrofit

    @Reactive
    fun reactiveRetrofit(): Retrofit

    @Cached
    fun cachedOkHttpClient(): OkHttpClient

    @NonCached
    fun okHttpClient(): OkHttpClient
}

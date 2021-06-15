package com.tilikki.training.unimager.demo.injector.module

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tilikki.training.unimager.demo.network.OAuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    @Cached
    @Singleton
    fun provideCachedOkHttpClient(cache: Cache): OkHttpClient {
        return getOAuthOkHttpBuilder()
            .cache(cache)
            .build()
    }

    @Provides
    @NonCached
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return getOAuthOkHttpBuilder()
            .build()
    }

    @Provides
    @Singleton
    @Callback
    fun provideRetrofit(
        baseUrl: String,
        gson: Gson,
        @Cached okHttpClient: OkHttpClient
    ): Retrofit {
        return getRetrofitBuilder(baseUrl, gson, okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Reactive
    fun provideReactiveRetrofit(
        baseUrl: String,
        gson: Gson,
        @Cached okHttpClient: OkHttpClient
    ): Retrofit {
        return getRetrofitBuilder(baseUrl, gson, okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun getOAuthOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor())
    }

    private fun getRetrofitBuilder(
        baseUrl: String,
        gson: Gson,
        @Cached okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(okHttpClient)
    }
}

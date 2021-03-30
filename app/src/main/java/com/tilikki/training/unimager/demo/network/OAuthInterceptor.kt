package com.tilikki.training.unimager.demo.network

import android.util.Log
import com.tilikki.training.unimager.demo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val tokenizedRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
            .build()

        val response = chain.proceed(tokenizedRequest)
        logRemainingTokenRequest(response)
        return response
    }

    private fun logRemainingTokenRequest(response: Response) {
        val logFormat = "Remaining request: ${response.header("X-Ratelimit-Remaining")} of ${response.header("X-Ratelimit-Limit")}"
        Log.i("UnimageFetcher", logFormat)
    }

}
package com.hlayan.weather.core.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class WeatherInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedUrl = originalRequest
            .url
            .newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()

        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()
        return chain.proceed(modifiedRequest)
    }

    companion object {
        private const val API_KEY = "501378c2efe447a796d120222240202"
    }
}
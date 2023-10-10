package com.inspius.sportsevent.api

import com.google.gson.GsonBuilder
import com.inspius.sportsevent.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Apis {

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val apiGson = GsonBuilder().create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(apiGson))
            .build()
    }

    val services: ApiServices by lazy { retrofit.create(ApiServices::class.java) }
}
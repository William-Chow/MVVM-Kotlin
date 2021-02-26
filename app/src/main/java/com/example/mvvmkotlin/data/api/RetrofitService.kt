package com.example.mvvmkotlin.data.api

import com.rx2androidnetworking.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    const val baseURL = "https://jsonplaceholder.typicode.com/"

    val retrofitClient: Retrofit.Builder by lazy {

        val levelType: Level = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            Level.BODY else Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)

        Retrofit.Builder().baseUrl(baseURL).client(okHttpClient.build()).addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiService by lazy { retrofitClient.build().create(ApiService::class.java) }
}
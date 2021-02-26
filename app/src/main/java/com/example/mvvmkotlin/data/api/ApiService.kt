package com.example.mvvmkotlin.data.api

import com.example.mvvmkotlin.data.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>
}
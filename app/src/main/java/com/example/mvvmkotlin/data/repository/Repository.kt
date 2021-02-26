package com.example.mvvmkotlin.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.mvvmkotlin.data.api.RetrofitService
import com.example.mvvmkotlin.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object Repository {
    val userRepository = MutableLiveData<List<User>>()

    fun getUser(): MutableLiveData<List<User>> {
        val callback = RetrofitService.apiInterface.getUsers()
        callback.enqueue(object : Callback<List<User>> {
            override fun onFailure(callback: Call<List<User>>, t: Throwable) {
                // Timber.i("Result :: " + t.message.toString())
            }

            override fun onResponse(callback: Call<List<User>>, response: Response<List<User>>) {
                // Timber.i("Result :: " + response.body().toString())
                val data = response.body()
                userRepository.value = data
            }
        })
        return userRepository
    }
}

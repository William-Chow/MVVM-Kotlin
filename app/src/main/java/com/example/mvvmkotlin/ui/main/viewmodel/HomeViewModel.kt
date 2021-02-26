package com.example.mvvmkotlin.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.data.model.User
import com.example.mvvmkotlin.data.repository.Repository

class HomeViewModel : ViewModel() {
    var userLiveData : MutableLiveData<List<User>>? = null

    fun getUser() : LiveData<List<User>>?{
        userLiveData = Repository.getUser()
        return userLiveData
    }
}
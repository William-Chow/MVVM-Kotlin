package com.example.mvvmkotlin.ui.main.viewmodel

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.data.model.User
import com.example.mvvmkotlin.data.repository.Repository

class HomeViewModel : ViewModel() {
    var userLiveData : MutableLiveData<List<User>>? = null

    fun getUser(pbLoading: ProgressBar): LiveData<List<User>>?{
        pbLoading.visibility = View.VISIBLE
        userLiveData = Repository.getUser()
        return userLiveData
    }
}
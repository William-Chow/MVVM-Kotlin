package com.example.mvvmkotlin.ui.main.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.data.model.User
import com.example.mvvmkotlin.databinding.ActivityHomeDetailBinding
import com.example.mvvmkotlin.ui.main.viewmodel.HomeViewModel
import com.example.mvvmkotlin.utils.Constant

class HomeDetail : AppCompatActivity(){

    private lateinit var context: Context
    private lateinit var homeViewModel: HomeViewModel // View Model

    private var user: User ?=null

    private lateinit var homeDetailBinding: ActivityHomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeDetailBinding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(homeDetailBinding.root)

        user = intent.getSerializableExtra(Constant.OBJECT) as? User

        context = this@HomeDetail // Context
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java) // init ViewModel

        homeDetailBinding.tvTitle.text = user?.name ?: ""
        homeDetailBinding.tvEmail.text = resources.getString(R.string.email) + " " + user?.email ?: ""
        homeDetailBinding.tvPhone.text = resources.getString(R.string.phone) + " " + user?.phone ?: ""
        homeDetailBinding.tvUsername.text = resources.getString(R.string.username) + " " + user?.username ?: ""
        homeDetailBinding.tvWebSite.text = resources.getString(R.string.website) + " " + user?.website ?: ""

        homeDetailBinding.ivBack.setOnClickListener{
            this.finish()
        }
    }
}
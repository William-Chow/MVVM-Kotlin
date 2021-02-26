package com.example.mvvmkotlin.ui.main.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.data.model.User
import com.example.mvvmkotlin.databinding.ActivityHomeBinding
import com.example.mvvmkotlin.ui.main.adapter.UserAdapter
import com.example.mvvmkotlin.ui.main.viewmodel.HomeViewModel
import com.example.mvvmkotlin.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

class Home : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var homeViewModel: HomeViewModel // View Model

    private lateinit var homeBinding: ActivityHomeBinding

    private var userList = ArrayList<User>() // ArrayList
    private var normalUserList = ArrayList<User>() // ArrayList
    private lateinit var userAdapter: UserAdapter
    private var recyclerViewUser: RecyclerView? = null
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        context = this@Home // Context
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java) // init ViewModel

        homeBinding.tvTitle.text = context.resources.getText(R.string.home)
        homeViewModel.getUser()!!.observe(this, { user ->
            // Timber.i("Result :: %s", user)
            userList = user as ArrayList<User>
            normalUserList = userList
            initAdapter(userList)
        })

        homeBinding.tvTitle.setOnClickListener {
            sortData()
        }
    }

    private fun sortData() {
        normalUserList.reverse()
        userAdapter.sorting(normalUserList)
    }

    private fun initAdapter(userList: ArrayList<User>) {
        userAdapter = UserAdapter(userList, context)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerViewUser = homeBinding.rvUser
        recyclerViewUser?.layoutManager = linearLayoutManager
        recyclerViewUser?.itemAnimator = DefaultItemAnimator()
        recyclerViewUser?.adapter = userAdapter
        userAdapter.onItemClick = { user ->
            Utils.intent(this, user, HomeDetail::class.java)
        }
    }
}

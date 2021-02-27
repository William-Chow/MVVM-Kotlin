package com.example.mvvmkotlin.ui.main.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
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

    private var doubleBackToExitPressedOnce = false

    private val handler = Handler() // handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        context = this@Home // Context
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java) // init ViewModel

        homeBinding.tvTitle.text = context.resources.getText(R.string.home)
        homeViewModel.getUser(homeBinding.pbLoading)!!.observe(this, { user ->
            // Timber.i("Result :: %s", user)
            userList = user as ArrayList<User>
            normalUserList = userList
            initAdapter(userList)
            homeBinding.pbLoading.visibility = View.GONE
        })

        homeBinding.tvTitle.setOnClickListener {
            sortData()
        }

        homeBinding.ivTurnOff.setOnClickListener{
            Toast.makeText(this, resources.getString(R.string.exit), Toast.LENGTH_LONG).show()
            handler.postDelayed(
                { this.finish() },
                2000
            ) // Close Activity after 2 seconds
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, resources.getString(R.string.back_pressed), Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
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

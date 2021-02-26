package com.example.mvvmkotlin.ui.main.view

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.data.model.Admin
import com.example.mvvmkotlin.databinding.ActivityRegisterBinding
import com.example.mvvmkotlin.ui.main.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import kotlin.random.Random

class Register : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var loginViewModel: LoginViewModel // View Model -- Sharing View Model

    private lateinit var registerBinding: ActivityRegisterBinding

    private lateinit var realm: Realm // Database

    private var isEmailValid = false
    private var isPasswordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        realm = Realm.getDefaultInstance()

        context = this@Register // Context
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java) // init ViewModel

        registerBinding.tvHaveAnAccount.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        registerBinding.btnRegister.setOnClickListener {
            validation()
        }

        registerBinding.tvHaveAnAccount.setOnClickListener{
            this.finish()
        }
    }

    private fun validation(){
        // Check for a valid email address.
        if (registerBinding.etRegisterUsername.text?.isEmpty() == true) {
            registerBinding.tilRegisterUsername.error = resources.getString(R.string.username_error)
            isEmailValid = false
        } else  {
            isEmailValid = true
            registerBinding.tilRegisterUsername.isErrorEnabled = false
        }

        // Check for a valid password.
        when {
            registerBinding.etRegisterPassword.text?.isEmpty() == true -> {
                registerBinding.tilRegisterPassword.error = resources.getString(R.string.password_error)
                isPasswordValid = false
            }
            registerBinding.etRegisterPassword.text!!.length < 6 -> {
                registerBinding.tilRegisterPassword.error = resources.getString(R.string.error_invalid_password)
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
                registerBinding.tilRegisterPassword.isErrorEnabled = false
            }
        }

        if (isEmailValid && isPasswordValid) {
            // Login Success
            registerSuccess(registerBinding.etRegisterUsername.text.toString(), registerBinding.etRegisterPassword.text.toString())
        }
    }

    private fun registerSuccess(username: String, password: String) {
//        realm.executeTransaction {
//            val admin = realm.createObject<Admin>(Random.nextInt(0, 100))
//            admin.username = username
//            admin.password = password
//        }
        Snackbar.make(registerBinding.root,"This is a simple Snackbar",Snackbar.LENGTH_LONG).show()
    }
}
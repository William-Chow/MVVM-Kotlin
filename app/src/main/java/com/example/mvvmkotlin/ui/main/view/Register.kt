package com.example.mvvmkotlin.ui.main.view

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.databinding.ActivityRegisterBinding
import com.example.mvvmkotlin.ui.main.viewmodel.LoginViewModel
import com.example.mvvmkotlin.utils.Utils.Companion.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm

class Register : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var loginViewModel: LoginViewModel // View Model -- Sharing View Model

    private lateinit var registerBinding: ActivityRegisterBinding

    private lateinit var realm: Realm // Database

    private var isUsernameValid = false
    private var isPasswordValid = false

    private val handler = Handler() // handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        realm = Realm.getDefaultInstance()

        context = this@Register // Context
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java) // init ViewModel

        registerBinding.tvHaveAnAccount.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        registerBinding.btnRegister.setOnClickListener {
            hideKeyboard()
            validation()
        }

        registerBinding.tvHaveAnAccount.setOnClickListener {
            this.finish()
        }
    }

    private fun validation() {
        // Check for a valid email address.
        if (registerBinding.etRegisterUsername.text?.isEmpty() == true) {
            registerBinding.tilRegisterUsername.error = resources.getString(R.string.username_error)
            isUsernameValid = false
        } else {
            isUsernameValid = true
            registerBinding.tilRegisterUsername.isErrorEnabled = false
        }

        // Check for a valid password.
        when {
            registerBinding.etRegisterPassword.text?.isEmpty() == true -> {
                registerBinding.tilRegisterPassword.error =
                    resources.getString(R.string.password_error)
                isPasswordValid = false
            }
            registerBinding.etRegisterPassword.text!!.length < 6 -> {
                registerBinding.tilRegisterPassword.error =
                    resources.getString(R.string.error_invalid_password)
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
                registerBinding.tilRegisterPassword.isErrorEnabled = false
            }
        }

        if (isUsernameValid && isPasswordValid) {
            clearEditTextField()
            // Login Success
            registerSuccess(
                registerBinding.etRegisterUsername.text.toString(),
                registerBinding.etRegisterPassword.text.toString()
            )
        }
    }

    private fun clearEditTextField() {
        registerBinding.etRegisterUsername.text?.clear()
        registerBinding.etRegisterPassword.text?.clear()
    }

    private fun registerSuccess(username: String, password: String) {
        loginViewModel.realmStoreData(username, password, realm) // Add Data to Realm Database
        Snackbar.make(
            registerBinding.root,
            resources.getString(R.string.register_success),
            Snackbar.LENGTH_LONG
        ).show()
        handler.postDelayed({ this.finish() }, 2000) // Close Activity after 2 seconds
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // close the realm when exit activity
    }
}
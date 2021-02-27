package com.example.mvvmkotlin.ui.main.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.debut.countrycodepicker.CountryPicker
import com.debut.countrycodepicker.data.Country
import com.debut.countrycodepicker.listeners.CountryCallBack
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.data.model.Admin
import com.example.mvvmkotlin.databinding.ActivityLoginBinding
import com.example.mvvmkotlin.ui.main.viewmodel.LoginViewModel
import com.example.mvvmkotlin.utils.Utils
import com.example.mvvmkotlin.utils.Utils.Companion.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.where
import timber.log.Timber

class Login : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var loginViewModel: LoginViewModel // View Model

    private lateinit var loginBinding: ActivityLoginBinding

    private var isUsernameValid = false
    private var isPasswordValid = false

    private lateinit var realm: Realm // Database

    private val handler = Handler() // handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        realm = Realm.getDefaultInstance()

        context = this@Login // Context
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java) // init ViewModel

        loginBinding.btnSubmit.setOnClickListener {
            hideKeyboard()
            validation()
        }

        loginBinding.tvSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        loginBinding.tvSignUp.setOnClickListener {
            clearEditTextField()
            // Sign Up
            Utils.intentWithoutFinish(this, Register::class.java)
        }

        // !!!Dangerous if you want to delete current DB (all)!!!
        // realm.beginTransaction()
        // added to delete the db once the activity is created. Only use this if required
        // realm.deleteAll()
        // realm.commitTransaction()

        loginBinding.tvCountries.setOnClickListener {
            CountryPicker.show(supportFragmentManager, object : CountryCallBack {
                override fun onCountrySelected(country: Country) {
                    loginBinding.tvCountries.text = country.name
                    // Snackbar.make(loginBinding.tvCountries, "Country : ${country.name} and Country Code : ${country.countryCode}", Snackbar.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        // Check Database Value All the database record, uncomment to view Admin Database

        val admins = realm.where<Admin>().findAll()
        for (admin in admins) {
            // body of loop
            Timber.i("Result :: %s + %s + %s", admin.username, admin.password, admin.key)
        }

    }

    @SuppressLint("TimberArgCount")
    private fun validation() {
        // Check for a valid email address.
        if (loginBinding.etUsername.text?.isEmpty() == true) {
            loginBinding.tilUsername.error = resources.getString(R.string.username_error)
            isUsernameValid = false
        } else {
            isUsernameValid = true
            loginBinding.tilUsername.isErrorEnabled = false
        }

        // Check for a valid password.
        when {
            loginBinding.etPassword.text?.isEmpty() == true -> {
                loginBinding.tilPassword.error = resources.getString(R.string.password_error)
                isPasswordValid = false
            }
            loginBinding.etPassword.text!!.length < 6 -> {
                loginBinding.tilPassword.error =
                    resources.getString(R.string.error_invalid_password)
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
                loginBinding.tilPassword.isErrorEnabled = false
            }
        }

        if (isUsernameValid && isPasswordValid) {
            // Login Success
            if(loginViewModel.realmValidateCheckingUser(loginBinding.etUsername.text.toString(), loginBinding.etPassword.text.toString(), realm) == 1) {
                loginSuccess()
            } else {
                // Invalid Login
                loginBinding.tilUsername.error = resources.getString(R.string.username_login_fail)
                isUsernameValid = false
                loginBinding.tilPassword.error = resources.getString(R.string.password_login_fail)
                isPasswordValid = false
                loginBinding.etUsername.requestFocus()
            }
            clearEditTextField()
        }
    }

    private fun loginSuccess() {
        Utils.snackBarCustom(loginBinding.root, resources.getString(R.string.login_success))
        handler.postDelayed(
            { Utils.intent(this, Home::class.java) },
            2000
        ) // Close Activity after 2 seconds
    }

    private fun clearEditTextField() {
        loginBinding.etUsername.text?.clear()
        loginBinding.etPassword.text?.clear()
        loginBinding.tvCountries.text = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // close the realm when exit activity
    }
}
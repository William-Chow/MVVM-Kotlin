package com.example.mvvmkotlin.ui.main.view

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.databinding.ActivityLoginBinding
import com.example.mvvmkotlin.ui.main.viewmodel.LoginViewModel
import com.example.mvvmkotlin.utils.Utils
import com.example.mvvmkotlin.utils.Utils.Companion.hideKeyboard
import io.realm.Realm

class Login : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var loginViewModel: LoginViewModel // View Model

    private lateinit var loginBinding: ActivityLoginBinding

    private var isUsernameValid = false
    private var isPasswordValid = false

    private lateinit var realm: Realm // Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        realm = Realm.getDefaultInstance()

        context = this@Login // Context
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java) // init ViewModel

        loginBinding.btnSubmit.setOnClickListener {
            clearEditTextField()
            hideKeyboard()
            validation()
        }

        loginBinding.tvSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        loginBinding.tvSignUp.setOnClickListener{
            clearEditTextField()
            // Sign Up
            Utils.intent(this, Register::class.java)
        }

        // !!!Dangerous if you want to delete current DB (all)!!!
        // realm.beginTransaction()
        // added to delete the db once the activity is created. Only use this if required
        // realm.deleteAll()
        // realm.commitTransaction()

//            CountryPicker.show(supportFragmentManager, object : CountryCallBack {
//                override fun onCountrySelected(country: Country) {
//                    Snackbar.make(homeDetailBinding.tvTitle, "Country : ${country.name} and Country Code : ${country.countryCode}",
//                        Snackbar.LENGTH_LONG).show()
//                }
//            })
    }

    override fun onResume() {
        super.onResume()
    }

    private fun validation(){
        // Check for a valid email address.
        if (loginBinding.etUsername.text?.isEmpty() == true) {
            loginBinding.tilUsername.error = resources.getString(R.string.username_error)
            isUsernameValid = false
        } else  {
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
                loginBinding.tilPassword.error = resources.getString(R.string.error_invalid_password)
                isPasswordValid = false
            }
            else -> {
                isPasswordValid = true
                loginBinding.tilPassword.isErrorEnabled = false
            }
        }

        if (isUsernameValid && isPasswordValid) {
            // Login Success
            validateLoginSuccess()
        }
    }

    private fun validateLoginSuccess(){
        Utils.intent(this, Home::class.java)
    }

    private fun clearEditTextField(){
        loginBinding.etUsername.text?.clear()
        loginBinding.etPassword.text?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // close the realm when exit activity
    }
}
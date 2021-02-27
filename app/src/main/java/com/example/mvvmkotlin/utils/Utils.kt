package com.example.mvvmkotlin.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.mvvmkotlin.data.model.User
import java.util.regex.Pattern

class Utils {
    companion object {

        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        fun <T> intent(activity: Activity, user: User, myClass: Class<T>) {
            val intent = Intent(activity, myClass)
            intent.putExtra(Constant.OBJECT, user)
            activity.startActivity(intent)
        }

        fun <T> intent(activity: Activity, myClass: Class<T>) {
            val intent = Intent(activity, myClass)
            activity.startActivity(intent)
        }

        fun isEmailValid(str: String): Boolean {
            return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
        }

        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }

        fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }

        @SuppressLint("ServiceCast")
        fun Context.hideKeyboard(view: View) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
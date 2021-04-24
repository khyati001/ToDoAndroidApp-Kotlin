package com.example.todoapp.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager

class Utils {

    companion object {
        fun hideSoftKeyboard(activity: Activity) {
            try {
                val inputMethodManager =
                    (activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                if (inputMethodManager.isActive) {
                    if (activity.currentFocus != null) {
                        inputMethodManager.hideSoftInputFromWindow(
                            activity.currentFocus!!.windowToken,
                            Constants.ZERO
                        )
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}
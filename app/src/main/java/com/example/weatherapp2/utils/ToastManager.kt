package com.example.weatherapp2.utils

import android.content.Context
import android.widget.Toast

class ToastManager {
    fun toast(context: Context, str: String, toastLength: Int) {
        Toast.makeText(
            context,
            str,
            toastLength
        ).show()
    }
}
package com.tilikki.training.unimager.demo.util

import android.content.Context
import android.widget.Toast

object LogUtility {
    fun showToast(context: Context, text: String, interval: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, interval).show()
    }

    const val LOGGER_APP_TAG = "Unimager"
    const val LOGGER_FETCH_TAG = "UnimageFetcher"
    const val LOGGER_BIND_TAG = "UnimageBinder"
    const val LOGGER_DATABASE_TAG = "UnimageDatabase"

}
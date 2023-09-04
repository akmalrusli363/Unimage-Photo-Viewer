package com.tilikki.training.unimager.demo.view.compose

import android.content.Context
import android.content.Intent
import android.net.Uri

object ComposeHelper {
    fun visitLink(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        context.startActivity(intent)
    }
}
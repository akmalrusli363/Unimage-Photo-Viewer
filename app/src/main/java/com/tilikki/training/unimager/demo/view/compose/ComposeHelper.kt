package com.tilikki.training.unimager.demo.view.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import com.tilikki.training.unimager.demo.R

object ComposeHelper {
    fun visitLink(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        context.startActivity(intent)
    }

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    @Composable
    fun getCircularProgressBar(): Painter {
        val image = AnimatedImageVector.animatedVectorResource(R.drawable.avd_loading)
        var atEnd by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            if (!atEnd) {
                atEnd = true
            }
        }
        return rememberAnimatedVectorPainter(image, atEnd)
    }
}
package com.tilikki.training.unimager.demo.util

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tilikki.training.unimager.demo.R
import org.jetbrains.annotations.NotNull

object ImageLoader {
    @DrawableRes val LOADING_PLACEHOLDER: Int = R.drawable.loading_animation
    @DrawableRes val BROKEN_PLACEHOLDER: Int = R.drawable.ic_general_error

    fun loadImage(imageUrl: String?, imageView: ImageView) {
        if (!imageUrl.isNullOrBlank()) {
            loadImage(LinkUtility.convertToUri(imageUrl), imageView)
        } else {
            loadResourceImage(LOADING_PLACEHOLDER, imageView)
        }
    }

    fun loadImage(imageUri: Uri?, imageView: ImageView) {
        imageView.layout(0, 0, 0, 0)
        if (imageUri != null){
            Glide.with(imageView)
                .load(imageUri)
                .placeholder(LOADING_PLACEHOLDER)
                .error(BROKEN_PLACEHOLDER)
                .into(imageView)
        } else {
            loadResourceImage(LOADING_PLACEHOLDER, imageView)
        }
    }

    fun loadResourceImage(@NotNull @DrawableRes imageRes: Int, imageView: ImageView) {
        imageView.layout(0, 0, 0, 0)
        imageView.setImageResource(imageRes)
    }
}
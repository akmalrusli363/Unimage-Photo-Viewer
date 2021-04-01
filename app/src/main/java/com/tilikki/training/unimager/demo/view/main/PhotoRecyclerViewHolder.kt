package com.tilikki.training.unimager.demo.view.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.training.unimager.demo.databinding.RecyclerViewPhotosBinding
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.util.ImageLoader
import com.tilikki.training.unimager.demo.util.LogUtility

class PhotoRecyclerViewHolder(private val itemBinding: RecyclerViewPhotosBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    private lateinit var item: Photo

    fun bind(photo: Photo) {
        item = photo
        Log.d(LogUtility.LOGGER_BIND_TAG, photo.toString())
        ImageLoader.loadImage(item.thumbnailUrl, itemBinding.ivPhotoImage)
        itemBinding.ivPhotoImage.contentDescription = item.description
        itemBinding.tvDescription.text = item.description
        itemBinding.llPhotoFrame.background = ColorDrawable(Color.parseColor(item.color))
    }
}
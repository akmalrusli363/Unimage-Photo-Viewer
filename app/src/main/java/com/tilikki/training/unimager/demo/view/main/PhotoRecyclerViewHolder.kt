package com.tilikki.training.unimager.demo.view.main

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.training.unimager.demo.databinding.RecyclerViewPhotosBinding
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.util.ImageLoader

class PhotoRecyclerViewHolder(private val itemBinding: RecyclerViewPhotosBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    private lateinit var item: Photo

    fun bind(photo: Photo) {
        item = photo
        Log.d("Unimage-Image", photo.toString())
        ImageLoader.loadImage(item.imageUrl.defaultSize, itemBinding.ivPhotoImage)
        itemBinding.ivPhotoImage.contentDescription = item.description
        itemBinding.tvDescription.text = item.description
    }
}
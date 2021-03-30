package com.tilikki.training.unimager.demo.view.main

import androidx.recyclerview.widget.RecyclerView
import com.tilikki.training.unimager.demo.databinding.RecyclerViewPhotosBinding
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.util.ImageLoader

class PhotoRecyclerViewHolder(private val itemBinding: RecyclerViewPhotosBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    private lateinit var item: Photo

    fun bind(photo: Photo) {
        item = photo
        ImageLoader.loadImage(item.linkUrl, itemBinding.ivPhotoImage)
        itemBinding.ivPhotoImage.contentDescription = item.description
    }
}
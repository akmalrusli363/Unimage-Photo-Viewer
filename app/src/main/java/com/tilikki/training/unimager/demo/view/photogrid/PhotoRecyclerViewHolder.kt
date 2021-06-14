package com.tilikki.training.unimager.demo.view.photogrid

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.training.unimager.demo.databinding.RecyclerViewPhotosBinding
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.util.ImageLoader
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivity

class PhotoRecyclerViewHolder(private val itemBinding: RecyclerViewPhotosBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    private lateinit var item: Photo

    fun bind(photo: Photo) {
        item = photo
        ImageLoader.loadImage(item.thumbnailUrl, itemBinding.ivPhotoImage)
        itemBinding.ivPhotoImage.contentDescription = item.altDescription
        itemBinding.llPhotoFrame.background = ColorDrawable(Color.parseColor(item.color))
        itemBinding.root.apply {
            setOnClickListener {
                val intent = Intent(context, PhotoDetailActivity::class.java)
                intent.putExtra(PhotoDetailActivity.INTENT_URL, item.id)
                context.startActivity(intent)
            }
        }
    }
}

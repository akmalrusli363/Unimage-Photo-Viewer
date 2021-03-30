package com.tilikki.training.unimager.demo.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tilikki.training.unimager.demo.databinding.RecyclerViewPhotosBinding
import com.tilikki.training.unimager.demo.model.Photo

class PhotoRecyclerViewAdapter : ListAdapter<Photo, PhotoRecyclerViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoRecyclerViewHolder {
        return PhotoRecyclerViewHolder(getRecyclerViewItemBinding(parent))
    }

    private fun getRecyclerViewItemBinding(parent: ViewGroup): RecyclerViewPhotosBinding {
        val inflater = LayoutInflater.from(parent.context)
        return RecyclerViewPhotosBinding.inflate(inflater, parent, false)
    }

    override fun onBindViewHolder(holder: PhotoRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
package com.tilikki.training.unimager.demo.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.view.main.PhotoRecyclerViewAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Photo>?) {
    val adapter = recyclerView.adapter as PhotoRecyclerViewAdapter
    adapter.submitList(data)
}
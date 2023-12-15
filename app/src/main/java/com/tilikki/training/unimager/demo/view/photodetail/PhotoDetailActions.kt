package com.tilikki.training.unimager.demo.view.photodetail

import android.content.Context

data class PhotoDetailActions(
    val downloadFile: (Context) -> Unit = {}
)
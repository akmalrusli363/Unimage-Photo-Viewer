package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName

data class PhotoUrl(
    @SerializedName("full")
    val fullSize: String,
    @SerializedName("regular")
    val regularSize: String,
    @SerializedName("small")
    val smallSize: String,
    @SerializedName("thumb")
    val thumbnailSize: String
)
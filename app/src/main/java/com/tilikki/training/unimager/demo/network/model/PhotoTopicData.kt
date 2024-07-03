package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName

data class PhotoTopicData(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("visibility")
    val visibility: String,
)
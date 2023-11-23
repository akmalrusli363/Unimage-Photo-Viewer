package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName

data class BasicUrlResponse(
    @SerializedName("url")
    val link: String,
)
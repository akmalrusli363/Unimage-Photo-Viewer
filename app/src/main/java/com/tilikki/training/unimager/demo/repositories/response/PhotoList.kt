package com.tilikki.training.unimager.demo.repositories.response

import com.google.gson.annotations.SerializedName
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto

data class PhotoList(
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("results")
    val results: List<NetworkPhoto>
)
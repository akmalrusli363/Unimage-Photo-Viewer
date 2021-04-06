package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName

data class LinkUrl(
    @SerializedName("self")
    val apiLink: String,
    @SerializedName("html")
    val webLink: String,
    @SerializedName("download")
    val downloadLink: String,
    @SerializedName("download_location")
    val apiDownloadLink: String
)
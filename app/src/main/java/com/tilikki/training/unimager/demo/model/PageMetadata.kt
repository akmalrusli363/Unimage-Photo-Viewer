package com.tilikki.training.unimager.demo.model

import java.io.Serializable

data class PageMetadata(
    var page: Int,
    val onEndOfDataAction: () -> Unit = {}
) : Serializable
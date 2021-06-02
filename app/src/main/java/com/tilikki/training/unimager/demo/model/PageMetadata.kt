package com.tilikki.training.unimager.demo.model

import java.io.Serializable

open class PageMetadata(
    var page: Int
) : Serializable {
    open fun onEndOfDataAction() {}
}
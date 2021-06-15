package com.tilikki.training.unimager.demo.view.photogrid.scroll

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tilikki.training.unimager.demo.model.PageMetadata

class FetchableEndlessScrollRecyclerListener(
    layoutManager: StaggeredGridLayoutManager?,
    private val pageMetadata: PageMetadata
) : EndlessRecyclerViewScrollListener(layoutManager) {
    override fun onLoadMore(totalItemsCount: Int, view: RecyclerView?) {
        pageMetadata.onEndOfDataAction()
    }
}

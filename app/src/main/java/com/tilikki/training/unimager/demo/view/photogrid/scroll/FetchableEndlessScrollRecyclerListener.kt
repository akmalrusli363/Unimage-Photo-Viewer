package com.tilikki.training.unimager.demo.view.photogrid.scroll

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tilikki.training.unimager.demo.model.PageMetadata

class FetchableEndlessScrollRecyclerListener(
    layoutManager: StaggeredGridLayoutManager?,
    private val pageMetadata: PageMetadata
) : EndlessRecyclerViewScrollListener(layoutManager) {
    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
        Log.d("deekk", "currennt page: ${pageMetadata.page}")
        pageMetadata.page = page + 1
        pageMetadata.onEndOfDataAction.invoke()
    }
}
package com.tilikki.training.unimager.demo.view.photogrid.scroll

import androidx.core.widget.NestedScrollView
import com.tilikki.training.unimager.demo.model.PageMetadata

class EndlessNestedScrollListener(private val pageMetadata: PageMetadata) :
    NestedScrollView.OnScrollChangeListener {
    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (v?.getChildAt(v.childCount - 1) != null) {
            if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                pageMetadata.onEndOfDataAction()
            }
        }

    }
}

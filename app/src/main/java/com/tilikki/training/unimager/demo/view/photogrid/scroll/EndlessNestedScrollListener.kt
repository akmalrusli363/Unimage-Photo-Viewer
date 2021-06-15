package com.tilikki.training.unimager.demo.view.photogrid.scroll

import androidx.core.widget.NestedScrollView
import com.tilikki.training.unimager.demo.model.PageMetadata

/**
 * The class contains NestedScrollView on scroll listener for nested view which
 * fetches new data when reached bottom of NestedScrollView (eg. RecyclerView).
 * <p>
 * This is a code that referred from
 * https://stackoverflow.com/questions/39894792/recyclerview-scrolllistener-inside-nestedscrollview
 * to address RecyclerView endless scroll behavior on NestedScrollView,
 * using NestedScrollView scroll listener directly instead of implementing
 * endless scrolling for RecyclerView.
 *
 * @author Govind
 */
class EndlessNestedScrollListener(private val pageMetadata: PageMetadata) :
    NestedScrollView.OnScrollChangeListener {

    override fun onScrollChange(
        v: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (v.getChildAt(v.childCount - 1) != null) {
            if (scrollY >= getMeasuredHeight(v) && scrollY > oldScrollY) {
                pageMetadata.onEndOfDataAction()
            }
        }

    }

    private fun getMeasuredHeight(v: NestedScrollView): Int {
        return v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight
    }

}

package com.tilikki.training.unimager.demo.view.photogrid.scroll;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jetbrains.annotations.NotNull;

/**
 * The class contains RecyclerView on scroll listener which fetches new data
 * when reached last data item on RecyclerView.
 * <p>
 * The tutorial:
 * https://guides.codepath.com/android/endless-scrolling-with-adapterviews-and-recyclerview
 * <p>
 * Source code: https://gist.github.com/nesquena/d09dc68ff07e845cc622
 *
 * @author Nathan Esquenazi (@nesquena)
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private final int startingPageIndex = 0;
    private final RecyclerView.LayoutManager mLayoutManager;
    /**
     * The minimum amount of items to have below your current scroll position before loading more.
     */
    private int visibleThreshold = 2;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(@NotNull RecyclerView view, int dx, int dy) {
        updateRecyclerViewItemState(view);
    }

    private void updateRecyclerViewItemState(RecyclerView view) {
        int totalItemCount = mLayoutManager.getItemCount();
        int lastVisibleItemPosition = getLastVisibleItemPosition();

        if (totalItemCount < previousTotalItemCount) {
            invalidateList(totalItemCount);
        }
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && reachedEndOfVisibleThreshold(totalItemCount, lastVisibleItemPosition)) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            loading = true;
        }
    }

    private boolean reachedEndOfVisibleThreshold(int totalItemCount, int lastVisibleItemPosition) {
        return (lastVisibleItemPosition + visibleThreshold) > totalItemCount;
    }

    private void invalidateList(int totalItemCount) {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = totalItemCount;
        if (totalItemCount == 0) {
            this.loading = true;
        }
    }

    private int getLastVisibleItemPosition() {
        int lastVisibleItemPosition = 0;
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    /**
     * Call this method whenever performing new searches
     */
    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    /**
     * Defines the process for actually loading more data based on page
     *
     * @param page            next page to fetch
     * @param totalItemsCount current item count
     * @param view            the recyclerView to invoke
     */
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

}
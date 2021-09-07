package com.tilikki.training.unimager.demo.view.photogrid

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.datasets.*
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.util.RecyclerViewItemCountAssertion
import com.tilikki.training.unimager.demo.util.isGone
import com.tilikki.training.unimager.demo.view.UnsplashRepoViewTest
import com.tilikki.training.unimager.demo.view.main.PhotoRecyclerViewHolder
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@MediumTest
class PhotoGridFragmentTest : UnsplashRepoViewTest() {

    private val samplePhotoId = "MyPhoto"
    private val samplePhotoIndex = 1
    private val samplePhotoList = generateSampleImageList()

    @Test
    fun previewImage_success() {
        val bundle = Bundle().apply {
            putParcelableArray(PhotoGridFragment.PHOTO_LIST, samplePhotoList.toTypedArray())
        }
        val scenario =
            launchFragmentInContainer<PhotoGridFragment>(bundle, R.style.Theme_UnimagePhotoViewer)

        val photoIndex = generateIndexedPhotoId(samplePhotoId, samplePhotoIndex)
        val photoDescription = generatePhotoAltDescription(photoIndex)
        val photoCount = samplePhotoList.size
        val endOfPhotoDescription = generateIndexedPhotoAltDescription(
            samplePhotoId, photoCount - 1
        )

        val recyclerView = onView(withId(R.id.rv_photos_grid))
        recyclerView
            .check(matches(isDisplayed()))
            .check(RecyclerViewItemCountAssertion(TestDataConstants.MAX_ITEMS_PER_PAGE))
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<PhotoRecyclerViewHolder>(photoCount - 1)
        )
        onView(withContentDescription(endOfPhotoDescription))
            .check(matches(isDisplayed()))
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<PhotoRecyclerViewHolder>(samplePhotoIndex - 1)
        )
        onView(withContentDescription(photoDescription))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.iv_photo_image))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(photoDescription)))
        onView(withId(R.id.tv_username))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_alt_description))
            .check(matches(withText(photoDescription)))

        Mockito.verify(fakeRepository).getPhotoDetail(photoIndex)
    }

    @Test
    fun previewEmpty_success() {
        val bundle = Bundle().apply {
            putParcelableArray(PhotoGridFragment.PHOTO_LIST, emptyArray())
        }
        val scenario =
            launchFragmentInContainer<PhotoGridFragment>(bundle, R.style.Theme_UnimagePhotoViewer)

        onView(withId(R.id.rv_photos_grid))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        onView(withId(R.id.ll_empty))
            .check(matches(isDisplayed()))
    }


    private fun generateSampleImageList(): List<Photo> {
        return EntityTestDataSet.generateSamplePhotoDataList(samplePhotoId)
    }
}

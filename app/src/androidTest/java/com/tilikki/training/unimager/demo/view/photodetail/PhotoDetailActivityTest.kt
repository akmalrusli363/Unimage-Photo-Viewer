package com.tilikki.training.unimager.demo.view.photodetail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.util.NestedScrollViewScrollAction
import com.tilikki.training.unimager.demo.util.isGone
import com.tilikki.training.unimager.demo.util.isVisible
import com.tilikki.training.unimager.demo.view.ViewTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class PhotoDetailActivityTest : ViewTest {

    private val samplePhotoId = "MyPhoto"
    private val samplePhotoDetail =
        EntityTestDataSet.generateSampleUserPhotoDetail(samplePhotoId)

    private val samplePhotoWithExifId = "MyPhoto-EXIF"
    private val samplePhotoWithExifDetail =
        EntityTestDataSet.generateSampleUserPhotoDetail(samplePhotoWithExifId)

    @Test
    fun hasPhotoId_simple_success() {
        val intent = Intent(getContext(), PhotoDetailActivity::class.java).apply {
            putExtra(PhotoDetailActivity.INTENT_URL, samplePhotoId)
        }
        val scenario = ActivityScenario.launch<PhotoDetailActivity>(intent)
        Thread.sleep(5000)

        Espresso.onView(withId(R.id.iv_photo_image))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(samplePhotoDetail.altDescription)))
        Espresso.onView(withId(R.id.tv_likes))
            .check(matches(isDisplayed()))
            .check(matches(withText(samplePhotoDetail.likes.toString())))
        Espresso.onView(withId(R.id.tv_username))
            .check(matches(isDisplayed()))
            .check(matches(withText(samplePhotoDetail.user.username)))
        Espresso.onView(withId(R.id.tv_full_name))
            .check(matches(isDisplayed()))
            .check(matches(withText(samplePhotoDetail.user.name)))
        Espresso.onView(withId(R.id.iv_profile_image))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(getDisplayFullName(samplePhotoDetail))))
        Espresso.onView(withId(R.id.tv_resolution))
            .perform(NestedScrollViewScrollAction())
            .check(matches(isDisplayed()))
            .check(matches(withText(getImageResolution(samplePhotoDetail))))
        Espresso.onView(withId(R.id.tv_description))
            .check(matches(withText(samplePhotoDetail.description)))
        Espresso.onView(withId(R.id.tv_alt_description))
            .check(matches(withText(samplePhotoDetail.altDescription)))
        Espresso.onView(withId(R.id.ll_exif))
            .check(isGone())
        Thread.sleep(1000)
        scenario.close()
    }

    @Test
    fun hasPhotoId_withExif_success() {
        val intent = Intent(getContext(), PhotoDetailActivity::class.java).apply {
            putExtra(PhotoDetailActivity.INTENT_URL, samplePhotoWithExifId)
        }
        val scenario = ActivityScenario.launch<PhotoDetailActivity>(intent)

        Thread.sleep(5000)
        Espresso.onView(withId(R.id.iv_photo_image))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(samplePhotoWithExifDetail.altDescription)))
        Espresso.onView(withId(R.id.tv_likes))
            .check(matches(isDisplayed()))
            .check(matches(withText(samplePhotoWithExifDetail.likes.toString())))
        Espresso.onView(withId(R.id.tv_username))
            .check(matches(isDisplayed()))
            .check(matches(withText(samplePhotoWithExifDetail.user.username)))
        Espresso.onView(withId(R.id.tv_full_name))
            .check(matches(isDisplayed()))
            .check(matches(withText(samplePhotoWithExifDetail.user.name)))
        Espresso.onView(withId(R.id.iv_profile_image))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(getDisplayFullName(samplePhotoWithExifDetail))))
        Espresso.onView(withId(R.id.tv_resolution))
            .perform(NestedScrollViewScrollAction())
            .check(matches(isDisplayed()))
            .check(matches(withText(getImageResolution(samplePhotoWithExifDetail))))
        Espresso.onView(withId(R.id.tv_description))
            .check(matches(withText(samplePhotoWithExifDetail.description)))
        Espresso.onView(withId(R.id.tv_alt_description))
            .check(matches(withText(samplePhotoWithExifDetail.altDescription)))
        Espresso.onView(withId(R.id.ll_exif))
            .perform(NestedScrollViewScrollAction())
            .check(isVisible())
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.tv_exif_brand))
            .check(matches(withText(TestDataConstants.DEMO_EXIF_BRAND)))
        Thread.sleep(1000)
        scenario.close()
    }

    @Test
    fun noPhotoId_leave_success() {
        val scenario = ActivityScenario.launch(PhotoDetailActivity::class.java)
        Espresso.onView(withId(R.id.iv_photo_image))
            .check(matches(not(isDisplayed())))
        scenario.close()
    }

    private fun getDisplayFullName(photoDetail: PhotoDetail): String {
        return getContext().resources.getString(
            R.string.username_format, photoDetail.user.name, photoDetail.user.username
        )
    }

    private fun getImageResolution(photoDetail: PhotoDetail): String {
        return getContext().resources.getString(
            R.string.image_size_full_format,
            photoDetail.width,
            photoDetail.height,
            photoDetail.getOrientation()
        )
    }
}

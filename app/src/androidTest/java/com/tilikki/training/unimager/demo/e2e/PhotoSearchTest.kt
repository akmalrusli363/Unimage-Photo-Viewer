package com.tilikki.training.unimager.demo.e2e

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.datasets.generateIndexedPhotoId
import com.tilikki.training.unimager.demo.datasets.generatePhotoAltDescription
import com.tilikki.training.unimager.demo.util.typeSearchViewText
import com.tilikki.training.unimager.demo.view.main.MainActivity
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivity
import com.tilikki.training.unimager.demo.view.profile.ProfileActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@LargeTest
@RunWith(AndroidJUnit4::class)
class PhotoSearchTest : EndToEndBaseTest() {

    private val firstSearchQuery = "flower"
    private val secondSearchQuery = "rainbow"
    private val firstPhotoIndex = 1
    private val firstSearchPhotoId = generateIndexedPhotoId(firstSearchQuery, firstPhotoIndex)
    private val secondSearchPhotoId = generateIndexedPhotoId(secondSearchQuery, firstPhotoIndex)

    @Test
    fun getPhotoDetail_toUserProfile_success() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        checkForInitialMainActivityState()
        performSearchPhoto(firstSearchQuery)

        Thread.sleep(2000)
        Mockito.verify(fakeRepository).getPhotos(firstSearchQuery)

        val firstPhotoAltDescription = generatePhotoAltDescription(firstSearchPhotoId)
        checkForPhotoGridFragmentAvailability(TestDataConstants.MAX_ITEMS_PER_PAGE)
        Espresso.onView(withContentDescription(firstPhotoAltDescription))
            .check(matches(isDisplayed()))
            .perform(click())

        Intents.intended(
            allOf(
                hasComponent(hasShortClassName(".view.photodetail.PhotoDetailActivity")),
                toPackage(getContext().packageName),
                hasExtra(PhotoDetailActivity.INTENT_URL, firstSearchPhotoId)
            )
        )
        Mockito.verify(fakeRepository).getPhotoDetail(firstSearchPhotoId)

        val username = TestDataConstants.DEMO_USERNAME
        checkPhotoDetail(firstPhotoAltDescription, username)
        Espresso.onView(withId(R.id.iv_profile_image))
            .perform(click())

        Intents.intended(
            allOf(
                hasComponent(hasShortClassName(".view.profile.ProfileActivity")),
                toPackage(getContext().packageName),
                hasExtra(ProfileActivity.INTENT_URL, username)
            )
        )
        Mockito.verify(fakeRepository).getUserProfile(username)
        Mockito.verify(fakeRepository).getUserPhotos(username)

        checkUserProfile(username)

        pressBack()
        Espresso.onView(withId(R.id.cl_profile_box))
            .check(matches(isDisplayed()))

        pressBack()
        Espresso.onView(withId(R.id.rv_photos_grid))
            .check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun multiple_search_success() {
        val username = TestDataConstants.DEMO_USERNAME
        val secondUsername = TestDataConstants.DEMO_USERNAME_ALT

        Mockito.doReturn(fakeRepository.getPhotos(secondSearchQuery, secondUsername))
            .`when`(fakeRepository).getPhotos(secondSearchQuery)
        Mockito.doReturn(fakeRepository.getPhotoDetail(secondSearchPhotoId, secondUsername))
            .`when`(fakeRepository).getPhotoDetail(secondSearchPhotoId)

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        checkForInitialMainActivityState()
        performSearchPhoto(firstSearchQuery)

        Thread.sleep(2000)
        Mockito.verify(fakeRepository).getPhotos(firstSearchQuery)

        val firstPhotoAltDescription = generatePhotoAltDescription(firstSearchPhotoId)
        checkForPhotoGridFragmentAvailability(TestDataConstants.MAX_ITEMS_PER_PAGE)
        Espresso.onView(withContentDescription(firstPhotoAltDescription))
            .check(matches(isDisplayed()))
            .perform(click())

        Intents.intended(
            allOf(
                hasComponent(hasShortClassName(".view.photodetail.PhotoDetailActivity")),
                toPackage(getContext().packageName),
                hasExtra(PhotoDetailActivity.INTENT_URL, firstSearchPhotoId)
            )
        )
        Mockito.verify(fakeRepository).getPhotoDetail(firstSearchPhotoId)

        checkPhotoDetail(firstPhotoAltDescription, username)

        pressBack()
        performSearchPhoto(secondSearchQuery, false)

        Thread.sleep(2000)
        Mockito.verify(fakeRepository).getPhotos(secondSearchQuery)

        val secondPhotoAltDescription = generatePhotoAltDescription(secondSearchPhotoId)
        checkForPhotoGridFragmentAvailability(TestDataConstants.MAX_ITEMS_PER_PAGE)
        Espresso.onView(withContentDescription(secondPhotoAltDescription))
            .check(matches(isDisplayed()))
            .perform(click())

        Intents.intended(
            allOf(
                hasComponent(hasShortClassName(".view.photodetail.PhotoDetailActivity")),
                toPackage(getContext().packageName),
                hasExtra(PhotoDetailActivity.INTENT_URL, secondSearchPhotoId)
            )
        )
        Mockito.verify(fakeRepository).getPhotoDetail(secondSearchPhotoId)

        checkPhotoDetail(secondPhotoAltDescription, secondUsername)

        pressBack()
        Espresso.onView(withId(R.id.sv_photo_search))
            .check(matches(isDisplayed()))

        scenario.close()
    }

    private fun performSearchPhoto(searchQuery: String, empty: Boolean = true) {
        if (empty) {
            Espresso.onView(withId(R.id.sv_photo_search))
                .perform(typeText("$searchQuery\n"))
        } else {
            Espresso.onView(withId(R.id.sv_photo_search))
                .check(matches(isDisplayed()))
                .perform(typeSearchViewText(searchQuery))
        }
    }

}

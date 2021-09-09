package com.tilikki.training.unimager.demo.e2e

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.VerificationModes
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.datasets.generateIndexedPhotoId
import com.tilikki.training.unimager.demo.datasets.generatePhotoAltDescription
import com.tilikki.training.unimager.demo.view.main.MainActivity
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivity
import com.tilikki.training.unimager.demo.view.profile.ProfileActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@LargeTest
@RunWith(AndroidJUnit4::class)
class UserAccessTest : EndToEndBaseTest() {

    private val username = TestDataConstants.DEMO_USERNAME
    private val firstSearchQuery = "flower"
    private val firstPhotoIndex = 1
    private val firstSearchPhotoId = generateIndexedPhotoId(firstSearchQuery, firstPhotoIndex)

    private val firstUserPhotoIndex = 1
    private val firstUserPhotoId = generateIndexedPhotoId(username, firstUserPhotoIndex)
    private val otherUserPhotoIndex = 3
    private val anotherUserPhotoId = generateIndexedPhotoId(username, otherUserPhotoIndex)

    @Test
    fun viewUserProfile_fromUserPhoto_success() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        checkForInitialMainActivityState()

        Espresso.onView(withId(R.id.sv_photo_search))
            .perform(typeText("$firstSearchQuery\n"))

        Thread.sleep(500)

        val firstPhotoAltDescription = generatePhotoAltDescription(firstSearchPhotoId)
        checkForPhotoGridFragmentAvailability(TestDataConstants.MAX_ITEMS_PER_PAGE)
        Espresso.onView(withContentDescription(firstPhotoAltDescription))
            .check(matches(isDisplayed()))
            .perform(click())

        checkPhotoDetail(firstPhotoAltDescription, username)
        Espresso.onView(withId(R.id.iv_profile_image))
            .perform(click())

        checkUserProfile(username)

        val firstUserPhotoAltDescription = generatePhotoAltDescription(firstUserPhotoId)
        checkForPhotoGridFragmentAvailability(TestDataConstants.DEMO_USER_TOTAL_PHOTOS)
        Espresso.onView(withContentDescription(firstUserPhotoAltDescription))
            .check(matches(isDisplayed()))
            .perform(click())

        checkPhotoDetail(firstUserPhotoAltDescription, username)
        Espresso.onView(withId(R.id.iv_profile_image))
            .perform(click())

        checkUserProfile(username)

        pressBack()
        checkPhotoDetail(firstPhotoAltDescription, username)

        pressBack()
        Espresso.onView(withId(R.id.rv_photos_grid))
            .check(matches(isDisplayed()))

        Mockito.verify(fakeRepository).getPhotos(firstSearchQuery)
        Mockito.verify(fakeRepository).getPhotoDetail(firstSearchPhotoId)
        Mockito.verify(fakeRepository).getPhotoDetail(firstUserPhotoId)
        Mockito.verify(fakeRepository, Mockito.times(2))
            .getUserProfile(username)
        Mockito.verify(fakeRepository, Mockito.times(2))
            .getUserPhotos(username)
        Intents.intended(
            allOf(
                hasComponent(hasShortClassName(".view.photodetail.PhotoDetailActivity")),
                toPackage(getContext().packageName),
                hasExtra(PhotoDetailActivity.INTENT_URL, firstSearchPhotoId)
            )
        )
        Intents.intended(
            allOf(
                hasComponent(hasShortClassName(".view.profile.ProfileActivity")),
                toPackage(getContext().packageName),
                hasExtra(ProfileActivity.INTENT_URL, username)
            ), VerificationModes.times(2)
        )

        scenario.close()
    }

}
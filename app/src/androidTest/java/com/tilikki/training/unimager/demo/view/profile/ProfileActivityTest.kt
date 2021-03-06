package com.tilikki.training.unimager.demo.view.profile

import android.content.Intent
import android.view.View
import androidx.annotation.PluralsRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.datasets.generateIndexedPhotoAltDescription
import com.tilikki.training.unimager.demo.injector.singleton.FakeRepositorySingleton
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.util.*
import com.tilikki.training.unimager.demo.view.UnsplashRepoViewTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@MediumTest
@RunWith(MockitoJUnitRunner::class)
class ProfileActivityTest : UnsplashRepoViewTest() {

    private val sampleUsername = TestDataConstants.DEMO_USERNAME
    private val sampleUsernameNoPhoto = TestDataConstants.DEMO_USERNAME_NO_PHOTO
    private val sampleUsernameError = TestDataConstants.DEMO_USERNAME_ERROR

    @Test
    fun hasUsername_fetch_success() {
        Mockito.doReturn(fakeRepository.getUserPhotos(sampleUsername, 15))
            .`when`(fakeRepository).getUserPhotos(sampleUsername)

        val intent = Intent(getContext(), ProfileActivity::class.java).apply {
            putExtra(ProfileActivity.INTENT_URL, sampleUsername)
        }
        val scenario = ActivityScenario.launch<ProfileActivity>(intent)
        val sampleUserProfile = EntityTestDataSet.generateSampleUserData(sampleUsername)

        Thread.sleep(2000)

        sampleUserProfile.run {
            onView(withId(R.id.iv_profile_image))
                .checkForCriteria(
                    isDisplayed(),
                    withContentDescription(getUserAvatarDescription(this))
                )
            onView(withId(R.id.tv_username))
                .checkForCriteria(isDisplayed(), withText(username))
            onView(withId(R.id.tv_full_name))
                .checkForCriteria(isDisplayed(), withText(name))
            onView(withId(R.id.tv_photos))
                .checkForCriteria(
                    isDisplayed(),
                    withText(formatPluralText(R.plurals.total_photos_format, totalPhotos))
                )
            onView(withId(R.id.tv_followers))
                .checkForCriteria(
                    isDisplayed(),
                    withText(formatPluralText(R.plurals.followers_format, followers.value()))
                )
            onView(withId(R.id.tv_following))
                .checkForCriteria(
                    isDisplayed(),
                    withText(formatPluralText(R.plurals.following_format, following.value()))
                )
        }

        val recyclerView = onView(withId(R.id.rv_photos_grid))
        val totalPhotos = TestDataConstants.DEMO_USER_TOTAL_PHOTOS
        onView(withId(R.id.ll_empty))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        onView(withId(R.id.ll_error))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        recyclerView.check(matches(isDisplayed()))
            .check(RecyclerViewItemCountAssertion(totalPhotos))
        onView(
            withContentDescription(generateIndexedPhotoAltDescription(sampleUsername, 1))
        ).check(matches(isDisplayed()))
        onView(
            withContentDescription(generateIndexedPhotoAltDescription(sampleUsername, totalPhotos))
        ).check(isVisible())

        scenario.close()
        Mockito.verify(fakeRepository).getUserProfile(sampleUsername)
        Mockito.verify(fakeRepository).getUserPhotos(sampleUsername)
    }

    @Test
    fun hasUsername_fetchNoPhotos_success() {
        Mockito.doReturn(fakeRepository.getUserPhotos(sampleUsernameNoPhoto, 0))
            .`when`(fakeRepository).getUserPhotos(sampleUsernameNoPhoto)

        val intent = Intent(getContext(), ProfileActivity::class.java).apply {
            putExtra(ProfileActivity.INTENT_URL, sampleUsernameNoPhoto)
        }
        val scenario = ActivityScenario.launch<ProfileActivity>(intent)
        val sampleUserProfile = EntityTestDataSet.generateNewUserData(sampleUsernameNoPhoto)

        Thread.sleep(2000)

        sampleUserProfile.run {
            onView(withId(R.id.iv_profile_image))
                .checkForCriteria(
                    isDisplayed(),
                ).check(matches(withContentDescription(getUserAvatarDescription(this))))
            onView(withId(R.id.tv_username))
                .checkForCriteria(isDisplayed(), withText(username))
            onView(withId(R.id.tv_full_name))
                .checkForCriteria(isDisplayed(), withText(name))
            onView(withId(R.id.tv_photos))
                .checkForCriteria(
                    isDisplayed(),
                    withText(formatPluralText(R.plurals.total_photos_format, 0))
                )
            onView(withId(R.id.tv_followers))
                .checkForCriteria(
                    isDisplayed(),
                    withText(formatPluralText(R.plurals.followers_format, followers.value()))
                )
            onView(withId(R.id.tv_following))
                .checkForCriteria(
                    isDisplayed(),
                    withText(formatPluralText(R.plurals.following_format, following.value()))
                )
        }

        onView(withId(R.id.rv_photos_grid))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        onView(withId(R.id.ll_error))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        onView(withId(R.id.ll_empty))
            .check(matches(isDisplayed()))

        scenario.close()
        Mockito.verify(fakeRepository).getUserProfile(sampleUsernameNoPhoto)
        Mockito.verify(fakeRepository).getUserPhotos(sampleUsernameNoPhoto)
    }

    @Test
    fun hasUsername_abrupt_error() {
        Mockito.doReturn(
            FakeRepositorySingleton.errorFakeRepository.getUserPhotos(sampleUsernameError)
        ).`when`(fakeRepository).getUserPhotos(sampleUsernameError)

        val intent = Intent(getContext(), ProfileActivity::class.java).apply {
            putExtra(ProfileActivity.INTENT_URL, sampleUsernameError)
        }
        val scenario = ActivityScenario.launch<ProfileActivity>(intent)

        Thread.sleep(3000)

        onView(withId(R.id.nsv_page))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        onView(withId(R.id.ll_error))
            .check(matches(isDisplayed()))

        scenario.close()
        Mockito.verify(fakeRepository).getUserProfile(sampleUsernameError)
        Mockito.verify(fakeRepository).getUserPhotos(sampleUsernameError)
    }

    private fun ViewInteraction.checkForCriteria(vararg viewMatchers: Matcher<View>): ViewInteraction {
        return this.check(matches(allOf(listOf(*viewMatchers))))
    }

    private fun getUserAvatarDescription(user: User): String {
        return getContext().resources.getString(R.string.user_avatar_description, user.name)
    }

    private fun formatPluralText(@PluralsRes plurals: Int, numValue: Int): String {
        return ViewUtility.displayPluralText(getContext().resources, plurals, numValue)
    }
}
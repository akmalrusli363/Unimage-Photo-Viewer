package com.tilikki.training.unimager.demo.e2e

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.util.RecyclerViewItemCountAssertion
import com.tilikki.training.unimager.demo.util.isGone
import com.tilikki.training.unimager.demo.view.UnsplashRepoViewTest
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before

abstract class EndToEndBaseTest : UnsplashRepoViewTest() {

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun finish() {
        Intents.release()
    }

    protected fun pressBack() {
        Espresso.onView(withContentDescription(R.string.abc_action_bar_up_description))
            .perform(click())
    }

    protected fun checkForPhotoGridFragmentAvailability(itemCount: Int) {
        Espresso.onView(withId(R.id.ll_empty))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        Espresso.onView(withId(R.id.rv_photos_grid))
            .check(matches(isDisplayed()))
            .check(RecyclerViewItemCountAssertion(itemCount))
    }

    protected fun checkForInitialMainActivityState() {
        Espresso.onView(withId(R.id.rv_photos_grid))
            .check(doesNotExist())
        Espresso.onView(withId(R.id.ll_empty))
            .check(doesNotExist())
    }

    protected fun checkUserProfile(username: String) {
        Espresso.onView(withId(R.id.tv_username))
            .check(matches(isDisplayed()))
            .check(matches(withText(username)))
        Espresso.onView(withId(R.id.tv_full_name))
            .check(matches(withText(username)))
        Espresso.onView(withId(R.id.iv_profile_image))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(getUserAvatarDescription(username))))
    }

    protected fun checkPhotoDetail(photoAltDescription: String, username: String) {
        Espresso.onView(withId(R.id.iv_photo_image))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(photoAltDescription)))
        Espresso.onView(withId(R.id.tv_username))
            .check(matches(withText(username)))
        Espresso.onView(withId(R.id.tv_full_name))
            .check(matches(withText(username)))
        Espresso.onView(withId(R.id.iv_profile_image))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(getDisplayFullName(username, username))))
    }

    protected fun getDisplayFullName(username: String, fullName: String): String {
        return getContext().resources.getString(
            R.string.username_format, username, fullName
        )
    }

    protected fun getUserAvatarDescription(username: String): String {
        return getContext().resources.getString(R.string.user_avatar_description, username)
    }
}
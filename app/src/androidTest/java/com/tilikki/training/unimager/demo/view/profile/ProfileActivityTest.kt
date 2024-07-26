package com.tilikki.training.unimager.demo.view.profile

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.filters.MediumTest
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.datasets.generateIndexedPhotoDescription
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.util.ViewUtility
import com.tilikki.training.unimager.demo.util.value
import com.tilikki.training.unimager.demo.view.UnsplashRepoViewTest
import com.tilikki.training.unimager.demo.view.photogrid.ComposeComponentNames
import org.junit.Test

@MediumTest
class ProfileActivityTest : UnsplashRepoViewTest() {

    private val sampleUsername = TestDataConstants.DEMO_USERNAME
    private val sampleUsernameNoPhoto = TestDataConstants.DEMO_USERNAME_NO_PHOTO
    private val sampleUsernameError = TestDataConstants.DEMO_USERNAME_ERROR

    @Test
    fun hasUsername_fetch_success() {
        val numOfPhotos = 30
        val sampleUserProfile =
            EntityTestDataSet.generateSampleUserData(sampleUsername, numOfPhotos)
        val sampleUserPhotos =
            EntityTestDataSet.generateSamplePhotoDataList(sampleUsername, numOfPhotos)
        var context = getContext()
        composeTestRule.setContent {
            context = LocalContext.current
            ProfileView(sampleUserProfile, sampleUserPhotos)
        }

        composeTestRule.run {
            waitForIdle()
            onNodeWithContentDescription(context.getUserAvatarDescription(sampleUserProfile))
                .assertExists()
            onNodeWithContentDescription(context.getString(R.string.account_username))
                .assertIsDisplayed()
                .assertTextEquals(sampleUserProfile.username)
            onNodeWithContentDescription(context.getString(R.string.account_full_name))
                .assertIsDisplayed()
                .assertTextEquals(sampleUserProfile.name)
            onNodeWithText(context.formatPluralText(R.plurals.total_photos_format, numOfPhotos))
                .assertIsDisplayed()
            onNodeWithText(
                context.formatPluralText(
                    R.plurals.followers_format,
                    sampleUserProfile.followers.value()
                )
            ).assertIsDisplayed()
            onNodeWithText(
                context.formatPluralText(
                    R.plurals.following_format,
                    sampleUserProfile.following.value()
                )
            ).assertIsDisplayed()

            onNodeWithTag(ComposeComponentNames.EMPTY_PHOTOS_SCREEN)
                .assertDoesNotExist()
            onNodeWithContentDescription(
                generateIndexedPhotoDescription(sampleUsername, 1)
            ).assertExists()
            onNodeWithTag(ComposeComponentNames.PROFILE_PHOTO_GRID).performScrollToIndex(numOfPhotos)
            onNodeWithContentDescription(
                generateIndexedPhotoDescription(sampleUsername, numOfPhotos)
            ).assertExists()
        }
    }

    @Test
    fun hasUsername_fetchNoPhotos_success() {
        val numOfPhotos = 0
        val sampleUserProfile =
            EntityTestDataSet.generateSampleUserData(sampleUsernameNoPhoto, numOfPhotos)
        val sampleUserPhotos =
            EntityTestDataSet.generateSamplePhotoDataList(sampleUsernameNoPhoto, numOfPhotos)
        var context = getContext()
        composeTestRule.setContent {
            context = LocalContext.current
            ProfileView(sampleUserProfile, sampleUserPhotos)
        }

        composeTestRule.run {
            waitForIdle()
            onNodeWithContentDescription(context.getUserAvatarDescription(sampleUserProfile))
                .assertExists()
            onNodeWithContentDescription(context.getString(R.string.account_username))
                .assertIsDisplayed()
                .assertTextEquals(sampleUserProfile.username)
            onNodeWithContentDescription(context.getString(R.string.account_full_name))
                .assertIsDisplayed()
                .assertTextEquals(sampleUserProfile.name)
            onNodeWithText(context.formatPluralText(R.plurals.total_photos_format, numOfPhotos))
                .assertIsDisplayed()
            onNodeWithText(
                context.formatPluralText(
                    R.plurals.followers_format,
                    sampleUserProfile.followers.value()
                )
            ).assertIsDisplayed()
            onNodeWithText(
                context.formatPluralText(
                    R.plurals.following_format,
                    sampleUserProfile.following.value()
                )
            ).assertIsDisplayed()

            onNodeWithContentDescription(
                generateIndexedPhotoDescription(sampleUsername, 1)
            ).assertDoesNotExist()
            onNodeWithTag(ComposeComponentNames.EMPTY_PHOTOS_SCREEN)
                .assertIsDisplayed()
        }
    }

    private fun Context.getUserAvatarDescription(user: User): String {
        return this.resources.getString(R.string.username_avatar_format, user.name, user.username)
    }

    private fun Context.formatPluralText(@PluralsRes plurals: Int, numValue: Int): String {
        return ViewUtility.displayPluralText(this.resources, plurals, numValue)
    }
}
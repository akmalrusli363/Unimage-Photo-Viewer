package com.tilikki.training.unimager.demo.view.photogrid

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.datasets.generateIndexedPhotoId
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData
import org.junit.Rule
import org.junit.Test

class PhotoCardTest {

    private val samplePhotoId = "MyPhoto"
    private val samplePhotoIndex = 1
    private val sampleUser = "LoremIpsum"

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun photoCardWithProfile_ifHaveUser_displayProfile() {
        val photoId = generateIndexedPhotoId(samplePhotoId, samplePhotoIndex)
        val photo = SampleComposePreviewData.generateSamplePhotoData(photoId)
        val user = SampleComposePreviewData.generateSampleUserData(sampleUser)

        composeTestRule.setContent {
            PhotoCardWithUserProfile(photo = photo, user = user)
        }

        composeTestRule
            .onNodeWithText("@${sampleUser}")
            .assertIsDisplayed()
    }

    @Test
    fun photoCardWithProfile_ifNoUser_doNotDisplayProfile() {
        val photoId = generateIndexedPhotoId(samplePhotoId, samplePhotoIndex)
        val photo = EntityTestDataSet.generateSamplePhotoData(photoId)

        composeTestRule.setContent {
            PhotoCard(photo = photo)
        }

        composeTestRule
            .onNodeWithText("@${sampleUser}")
            .assertIsDisplayed()
    }
}
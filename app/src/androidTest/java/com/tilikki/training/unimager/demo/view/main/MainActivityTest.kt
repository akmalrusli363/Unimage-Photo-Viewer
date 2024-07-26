package com.tilikki.training.unimager.demo.view.main

import android.util.Log
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.filters.MediumTest
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.datasets.generateIndexedPhotoDescription
import com.tilikki.training.unimager.demo.datasets.generateIndexedPhotoId
import com.tilikki.training.unimager.demo.injector.singleton.FakeRepositorySingleton
import com.tilikki.training.unimager.demo.view.UnsplashRepoViewTest
import com.tilikki.training.unimager.demo.view.photogrid.ComposeComponentNames
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

@MediumTest
class MainActivityTest : UnsplashRepoViewTest() {

    private val sampleSearchQuery = "animals"
    private val firstPhotoIndex = 1
    private val lastPhotoIndex = TestDataConstants.MAX_ITEMS_PER_PAGE
    private val firstPhotoId = generateIndexedPhotoId(sampleSearchQuery, firstPhotoIndex)
    private val lastPhotoId = generateIndexedPhotoId(sampleSearchQuery, lastPhotoIndex)

    @Test
    fun search_success() {
        val fakeRepository = FakeRepositorySingleton.fakeUnsplashPagingRepository
        val requiredValue = 5
        val viewModel = MainViewModel(fakeRepository)
        val assertedDescription = generateIndexedPhotoDescription(sampleSearchQuery, 1)
        Mockito.doReturn(
            FakeRepositorySingleton.fakeUnsplashPagingRepository.getPhotos(
                sampleSearchQuery,
                requiredValue
            )
        ).`when`(fakeRepository).getPhotos(sampleSearchQuery)

        composeTestRule.setContent {
            PhotoSearchScreen(viewModel = viewModel)
        }

        composeTestRule.run {
            onRoot(useUnmergedTree = true)
            onNodeWithText("Search photos")
                .assertIsDisplayed()

            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).performTextInput(sampleSearchQuery)
            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).assert(hasText(sampleSearchQuery))
            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).performImeAction()
            waitForIdle()

            onNodeWithTag(ComposeComponentNames.PAGING_PHOTO_GRID).assertIsDisplayed()
            onAllNodes(hasParent(hasTestTag(ComposeComponentNames.PAGING_PHOTO_GRID)))
                .assertCountEquals(requiredValue)

            onNodeWithTag(ComposeComponentNames.PAGING_PHOTO_GRID).printToLog("mainActivityTest")
            Log.d(
                "mainActivityTest",
                "Asserting photo card with content description of '$assertedDescription'"
            )
            onNodeWithContentDescription(label = assertedDescription)
                .assertIsDisplayed()
        }
        Mockito.verify(fakeRepository).getPhotos(sampleSearchQuery)
    }

    @Test
    fun search_success_lotOfPages() {
        val fakeRepository = FakeRepositorySingleton.fakeUnsplashPagingRepository
        val requiredSize = 50
        Mockito.doReturn(fakeRepository.getPhotos(sampleSearchQuery, requiredSize))
            .`when`(fakeRepository).getPhotos(sampleSearchQuery)

        val viewModel = MainViewModel(fakeRepository)
        val firstDescription = generateIndexedPhotoDescription(sampleSearchQuery, 1)
        val lastDescription = generateIndexedPhotoDescription(sampleSearchQuery, requiredSize)
        val outRangeDescription =
            generateIndexedPhotoDescription(sampleSearchQuery, requiredSize + 1)

        composeTestRule.setContent {
            PhotoSearchScreen(viewModel = viewModel)
        }

        composeTestRule.run {
            onRoot(useUnmergedTree = true)
            onNodeWithText("Search photos")
                .assertIsDisplayed()

            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).performTextInput(sampleSearchQuery)
            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).assert(hasText(sampleSearchQuery))
            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).performImeAction()
            waitForIdle()

            onNodeWithTag(ComposeComponentNames.PAGING_PHOTO_GRID).assertIsDisplayed()

            onNodeWithTag(ComposeComponentNames.PAGING_PHOTO_GRID).printToLog("mainActivityTest")
            Log.d(
                "mainActivityTest",
                "Asserting photo card with content description of '$firstDescription'"
            )
            onNodeWithContentDescription(label = firstDescription)
                .assertIsDisplayed()
            onNodeWithTag(ComposeComponentNames.PAGING_PHOTO_GRID).performScrollToIndex(requiredSize)
            onNodeWithContentDescription(label = lastDescription)
                .assertIsDisplayed()
            Assert.assertThrows(IllegalArgumentException::class.java) {
                onNodeWithTag(ComposeComponentNames.PAGING_PHOTO_GRID).performScrollToIndex(
                    requiredSize + 1
                )
            }
            onNodeWithContentDescription(label = outRangeDescription)
                .assertDoesNotExist()
        }
        Mockito.verify(fakeRepository, Mockito.atLeastOnce()).getPhotos(sampleSearchQuery)
    }

    @Test
    fun search_empty_success() {
        val query = TestDataConstants.DEMO_SEARCH_EMPTY
        val fakeRepository = FakeRepositorySingleton.emptyFakePagingRepository
        val viewModel = MainViewModel(fakeRepository)

        composeTestRule.setContent {
            PhotoSearchScreen(viewModel = viewModel)
        }

        composeTestRule.run {
            onRoot(useUnmergedTree = true)
            onNodeWithText("Search photos")
                .assertIsDisplayed()

            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).performTextInput(query)
            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).assert(hasText(query))
            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).performImeAction()
            waitForIdle()

            onNodeWithTag(ComposeComponentNames.PAGING_PHOTO_GRID).assertDoesNotExist()
            onNodeWithTag(ComposeComponentNames.EMPTY_PHOTOS_SCREEN)
                .assertIsDisplayed()
        }
    }

    @Test
    fun search_abrupt_returnError() {
        val query = TestDataConstants.DEMO_SEARCH_ERROR
        val fakeRepository = FakeRepositorySingleton.errorFakePagingRepository
        val viewModel = MainViewModel(fakeRepository)

        composeTestRule.setContent {
            PhotoSearchScreen(viewModel = viewModel)
        }

        composeTestRule.run {
            onRoot(useUnmergedTree = true)
            onNodeWithText("Search photos")
                .assertIsDisplayed()

            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).performTextInput(query)
            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).assert(hasText(query))
            onNodeWithTag(ComposeComponentNames.PHOTO_SEARCH).performImeAction()
            waitForIdle()

            onNodeWithTag(ComposeComponentNames.PAGING_PHOTO_GRID).assertDoesNotExist()
            onNodeWithTag(ComposeComponentNames.ERROR_STATE_SCREEN)
                .assertIsDisplayed()
        }
    }
}

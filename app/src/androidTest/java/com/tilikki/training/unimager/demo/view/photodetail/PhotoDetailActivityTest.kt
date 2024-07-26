package com.tilikki.training.unimager.demo.view.photodetail

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.filters.MediumTest
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.datasets.NetworkTestDataSet
import com.tilikki.training.unimager.demo.injector.singleton.FakeRepositorySingleton
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.view.UnsplashRepoViewTest
import com.tilikki.training.unimager.demo.view.photogrid.ComposeComponentNames
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.mockito.Mockito

@MediumTest
class PhotoDetailActivityTest : UnsplashRepoViewTest() {

    private val samplePhotoId = "MyPhoto"
    private val samplePhotoDetail =
        NetworkTestDataSet.generateSamplePhotoData(samplePhotoId).toDomainEntityPhotoDetail()

    private val samplePhotoWithExifId = "MyPhoto-EXIF"
    private val samplePhotoWithExifDetail =
        NetworkTestDataSet.generateSamplePhotoData(samplePhotoWithExifId)
            .toDomainEntityPhotoDetail()

    private val sampleErrorPhotoId = "MyPhoto~!3$"

    @Test
    fun hasPhotoId_simple_success() {
        val viewModel = PhotoDetailViewModel(fakeRepository)
        viewModel.attachPhoto(samplePhotoId)

        lateinit var context: Context
        composeTestRule.setContent {
            context = LocalContext.current
            PhotoDetailScreen(viewModel = viewModel)
        }

        composeTestRule.run {
            onRoot(useUnmergedTree = true)
            waitForIdle()

            onNodeWithContentDescription(samplePhotoDetail.description!!).assertExists()
            onNodeWithContentDescription("${samplePhotoDetail.likes} likes", useUnmergedTree = true)
                .onChildren()
                .filterToOne(hasText(samplePhotoDetail.likes.toString()))
                .assertExists()

            samplePhotoDetail.let { photo ->
                val profileCard = onNodeWithContentDescription(
                    context.getDisplayFullName(photo),
                    useUnmergedTree = true
                ).assertExists()
                profileCard.onChildAt(0).assertExists()
                    .assert(hasContentDescription("${photo.user.name} (@${photo.user.username})'s avatar"))
                profileCard.onChildAt(1).assertExists().run {
                    onNodeWithText(photo.user.username).assertExists()
                    onNodeWithText(photo.user.name).assertExists()
                }
                onNodeWithContentDescription(
                    context.getString(R.string.image_resolution),
                    useUnmergedTree = true
                ).assertExists()
                    .onChildAt(1)
                    .assertTextEquals(context.getImageResolution(samplePhotoDetail))
                onNodeWithContentDescription(
                    context.getString(R.string.description),
                    useUnmergedTree = true
                ).assertExists()
                    .onChildAt(1)
                    .assertTextEquals(photo.description!!)
                onNodeWithContentDescription(
                    context.getString(R.string.alt_description),
                    useUnmergedTree = true
                ).assertExists()
                    .onChildAt(1)
                    .assertTextEquals(photo.altDescription!!)
                onNodeWithText(context.getString(R.string.exif))
                    .assertDoesNotExist()
            }
        }

        Mockito.verify(fakeRepository).getPhotoDetail(samplePhotoId)
        Mockito.verify(fakeRepository).getRandomPhotosByTopic("")
    }

    @Test
    fun hasPhotoId_withExif_success() {
        val viewModel = PhotoDetailViewModel(fakeRepository)
        viewModel.attachPhoto(samplePhotoWithExifId)

        lateinit var context: Context
        composeTestRule.setContent {
            context = LocalContext.current
            PhotoDetailScreen(viewModel = viewModel)
        }

        composeTestRule.run {
            onRoot(useUnmergedTree = true)
            waitForIdle()

            onNodeWithContentDescription(samplePhotoWithExifDetail.description!!).assertExists()
            onNodeWithContentDescription(
                "${samplePhotoWithExifDetail.likes} likes",
                useUnmergedTree = true
            )
                .onChildren()
                .filterToOne(hasText(samplePhotoWithExifDetail.likes.toString()))
                .assertExists()

            samplePhotoWithExifDetail.let { photo ->
                val profileCard = onNodeWithContentDescription(
                    context.getDisplayFullName(photo),
                    useUnmergedTree = true
                ).assertExists()
                profileCard.onChildAt(0).assertExists()
                    .assert(hasContentDescription("${photo.user.name} (@${photo.user.username})'s avatar"))
                profileCard.onChildAt(1).assertExists().run {
                    onNodeWithText(photo.user.username).assertExists()
                    onNodeWithText(photo.user.name).assertExists()
                }
                onNodeWithContentDescription(
                    context.getString(R.string.image_resolution),
                    useUnmergedTree = true
                ).assertExists()
                    .onChildAt(1)
                    .assertTextEquals(context.getImageResolution(samplePhotoDetail))
                onNodeWithContentDescription(
                    context.getString(R.string.description),
                    useUnmergedTree = true
                ).assertExists()
                    .onChildAt(1)
                    .assertTextEquals(photo.description!!)
                onNodeWithContentDescription(
                    context.getString(R.string.alt_description),
                    useUnmergedTree = true
                ).assertExists()
                    .onChildAt(1)
                    .assertTextEquals(photo.altDescription!!)

                onNodeWithText(context.getString(R.string.exif))
                    .assertExists()

                onNodeWithContentDescription(
                    context.getString(R.string.exif_model),
                    useUnmergedTree = true
                ).assertExists()
                    .onChildAt(1)
                    .assertTextEquals(photo.exif?.model!!)

                onNodeWithContentDescription(
                    context.getString(R.string.exif_brand),
                    useUnmergedTree = true
                ).assertExists()
                    .onChildAt(1)
                    .assertTextEquals(photo.exif?.brand!!)
            }
        }

        Mockito.verify(fakeRepository).getPhotoDetail(samplePhotoWithExifId)
        Mockito.verify(fakeRepository).getRandomPhotosByTopic("")
    }

    @Test
    fun hasPhotoId_abrupt_error() {
        val fakeErrorRepository = FakeRepositorySingleton.errorFakeRepository
        val viewModel = PhotoDetailViewModel(fakeErrorRepository)
        viewModel.attachPhoto(sampleErrorPhotoId)

        lateinit var context: Context
        composeTestRule.setContent {
            context = LocalContext.current
            PhotoDetailScreen(viewModel = viewModel)
        }

        composeTestRule.run {
            onRoot(useUnmergedTree = true)
            waitForIdle()

            onNodeWithContentDescription(
                context.getString(R.string.description),
                useUnmergedTree = true
            ).assertDoesNotExist()

            onNodeWithText(context.getString(R.string.error_occurred))
                .assertExists()
            onNodeWithTag(ComposeComponentNames.ERROR_STATE_SCREEN)
                .assertIsDisplayed()
        }
    }

    //        Mockito.doReturn(
//            FakeRepositorySingleton.errorFakeRepository.getPhotoDetail(sampleErrorPhotoId)
//        ).`when`(fakeRepository).getPhotoDetail(sampleErrorPhotoId)
//
//        val intent = Intent(getContext(), PhotoDetailActivity::class.java).apply {
//            putExtra(PhotoDetailActivity.INTENT_URL, sampleErrorPhotoId)
//        }
//        val scenario = ActivityScenario.launch<PhotoDetailActivity>(intent)
//
//        Thread.sleep(2000)
//
//        Espresso.onView(withId(R.id.nsv_page))
//            .check(matches(CoreMatchers.not(isDisplayed())))
//            .check(isGone())
//        Espresso.onView(withId(R.id.ll_error))
//            .check(matches(isDisplayed()))
//
//        scenario.close()
//        Mockito.verify(fakeRepository).getPhotoDetail(sampleErrorPhotoId)
//    }
//
    @Test
    fun noPhotoId_leave_success() {
        val scenario = ActivityScenario.launch(PhotoDetailActivity::class.java)
        MatcherAssert.assertThat(scenario.state, Matchers.`is`(Lifecycle.State.DESTROYED))
        scenario.close()
    }

    private fun Context.getDisplayFullName(photoDetail: PhotoDetail): String {
        return this.resources.getString(
            R.string.username_format, photoDetail.user.name, photoDetail.user.username
        )
    }

    private fun Context.getImageResolution(photoDetail: PhotoDetail): String {
        return this.resources.getString(
            R.string.image_size_full_format,
            photoDetail.width,
            photoDetail.height,
            photoDetail.getOrientation()
        )
    }
}

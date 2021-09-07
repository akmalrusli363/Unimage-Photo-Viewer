package com.tilikki.training.unimager.demo.view.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.datasets.generateIndexedPhotoId
import com.tilikki.training.unimager.demo.datasets.generatePhotoAltDescription
import com.tilikki.training.unimager.demo.injector.singleton.FakeRepositorySingleton
import com.tilikki.training.unimager.demo.util.RecyclerViewItemCountAssertion
import com.tilikki.training.unimager.demo.util.isGone
import com.tilikki.training.unimager.demo.view.UnsplashRepoViewTest
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@MediumTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest : UnsplashRepoViewTest() {

    private val sampleSearchQuery = "animals"
    private val firstPhotoIndex = 1
    private val lastPhotoIndex = TestDataConstants.MAX_ITEMS_PER_PAGE
    private val firstPhotoId = generateIndexedPhotoId(sampleSearchQuery, firstPhotoIndex)
    private val lastPhotoId = generateIndexedPhotoId(sampleSearchQuery, lastPhotoIndex)

    @Test
    fun search_success() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.rv_photos_grid))
            .check(doesNotExist())
        onView(withId(R.id.ll_empty))
            .check(doesNotExist())
        onView(withId(R.id.sv_photo_search))
            .perform(ViewActions.typeText("$sampleSearchQuery\n"))

        Thread.sleep(2000)

        val recyclerView = onView(withId(R.id.rv_photos_grid))
        onView(withId(R.id.ll_empty))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        recyclerView.check(matches(isDisplayed()))
            .check(RecyclerViewItemCountAssertion(TestDataConstants.MAX_ITEMS_PER_PAGE))
        onView(withContentDescription(generatePhotoAltDescription(firstPhotoId)))
            .check(matches(isDisplayed()))
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<PhotoRecyclerViewHolder>(lastPhotoIndex - 1)
        )
        onView(withContentDescription(generatePhotoAltDescription(lastPhotoId)))
            .check(matches(isDisplayed()))
        scenario.close()
        Mockito.verify(fakeRepository).getPhotos(sampleSearchQuery)
    }

    @Test
    fun search_empty_success() {
        val query = TestDataConstants.DEMO_SEARCH_EMPTY
        Mockito.doReturn(FakeRepositorySingleton.emptyFakeRepository.getPhotos(query))
            .`when`(fakeRepository).getPhotos(query)

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.rv_photos_grid))
            .check(doesNotExist())
        onView(withId(R.id.ll_empty))
            .check(doesNotExist())
        onView(withId(R.id.sv_photo_search))
            .perform(ViewActions.typeText("$query\n"))

        Thread.sleep(3000)

        onView(withId(R.id.rv_photos_grid))
            .check(matches(not(isDisplayed())))
            .check(isGone())
        onView(withId(R.id.ll_empty))
            .check(matches(isDisplayed()))
        scenario.close()
        Mockito.verify(fakeRepository).getPhotos(query)
    }

    @Test
    fun search_abrupt_returnError() {
        val query = TestDataConstants.DEMO_SEARCH_ERROR
        Mockito.doReturn(FakeRepositorySingleton.errorFakeRepository.getPhotos(query))
            .`when`(fakeRepository).getPhotos(query)

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.rv_photos_grid))
            .check(doesNotExist())
        onView(withId(R.id.ll_empty))
            .check(doesNotExist())
        onView(withId(R.id.sv_photo_search))
            .perform(ViewActions.typeText("$query\n"))

        Thread.sleep(3000)

        onView(withId(R.id.rv_photos_grid))
            .check(doesNotExist())
        onView(withId(R.id.ll_error))
            .check(matches(isDisplayed()))
        scenario.close()
        Mockito.verify(fakeRepository).getPhotos(query)
    }

}

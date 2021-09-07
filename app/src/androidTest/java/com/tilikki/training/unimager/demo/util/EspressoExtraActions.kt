package com.tilikki.training.unimager.demo.util

import android.view.View
import android.widget.SearchView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

fun typeSearchViewText(text: String, submit: Boolean = true): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
        }

        override fun getDescription(): String {
            return "Change view text";
        }

        override fun perform(uiController: UiController, view: View) {
            (view as SearchView).setQuery(text, submit);
        }
    }
}
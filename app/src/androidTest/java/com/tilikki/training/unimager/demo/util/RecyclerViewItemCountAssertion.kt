package com.tilikki.training.unimager.demo.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers.`is`

// Assert RecyclerView item count
// https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso
class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        ViewMatchers.assertThat(adapter!!.itemCount, `is`(expectedCount))
    }

}

package com.tilikki.training.unimager.demo.util

import android.view.View
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

// View visibility assertion https://gist.github.com/elevenetc/8038fe98bc603b91b288
fun isVisible(): ViewAssertion {
    return ViewAssertion { view, noViewException ->
        assertThat(
            view,
            VisibilityMatcher(View.VISIBLE)
        )
    }
}

fun isInvisible(): ViewAssertion {
    return ViewAssertion { view, noViewException ->
        assertThat(
            view,
            VisibilityMatcher(View.INVISIBLE)
        )
    }
}

fun isGone(): ViewAssertion {
    return ViewAssertion { view, noViewException ->
        assertThat(
            view,
            VisibilityMatcher(View.GONE)
        )
    }
}

class VisibilityMatcher(private val visibility: Int) : BaseMatcher<View>() {

    override fun describeTo(description: Description) {
        val visibilityName =
            when (visibility) {
                View.GONE -> "GONE"
                View.VISIBLE -> "VISIBLE"
                else -> "INVISIBLE"
            }
        description.appendText("View visibility must has equals $visibilityName")
    }

    override fun matches(o: Any?): Boolean {
        if (o == null) {
            return (visibility == View.GONE || visibility == View.INVISIBLE)
        }
        if (o !is View)
            throw IllegalArgumentException("Object must be instance of View. Object is instance of $o")
        return o.visibility == visibility
    }
}

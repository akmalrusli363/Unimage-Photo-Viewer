package com.tilikki.training.unimager.demo.view

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule

abstract class ComposeViewTest : ViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    protected fun setContentAndGetContext(composable: @Composable () -> Unit): Context {
        lateinit var context: Context
        composeTestRule.setContent {
            context = LocalContext.current
            composable.invoke()
        }
        return context
    }

    protected fun Context.onNodeWithContentDescription(
        @StringRes stringRes: Int,
        substring: Boolean = false,
        ignoreCase: Boolean = false,
        useUnmergedTree: Boolean = false
    ): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithContentDescription(
            label = this.getString(stringRes),
            substring = substring,
            ignoreCase = ignoreCase,
            useUnmergedTree = useUnmergedTree
        )
    }
}
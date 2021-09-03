package com.tilikki.training.unimager.demo.view

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

interface ViewTest {
    fun getContext(): Context {
        return InstrumentationRegistry.getInstrumentation().targetContext
    }
}

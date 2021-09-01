package com.tilikki.training.unimager.demo

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.tilikki.training.unimager.demo.core.UnimageTestApplication

class AppTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, UnimageTestApplication::class.java.name, context)
    }
}

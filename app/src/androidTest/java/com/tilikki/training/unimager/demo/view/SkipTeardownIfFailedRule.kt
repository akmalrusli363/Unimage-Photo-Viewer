package com.tilikki.training.unimager.demo.view

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class SkipTeardownIfFailedRule : TestWatcher() {
    private var testSuccess = false

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        testSuccess = true
    }

    fun isTestSuccess(): Boolean {
        return testSuccess
    }
}
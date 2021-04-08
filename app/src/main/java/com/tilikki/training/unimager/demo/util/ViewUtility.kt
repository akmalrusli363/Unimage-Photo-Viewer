package com.tilikki.training.unimager.demo.util

import android.view.View

object ViewUtility {
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun toggleVisibilityPairs(trueView: View, falseView: View, value: Boolean) {
        setVisibility(trueView, value)
        setVisibility(falseView, !value)
    }
}
package com.tilikki.training.unimager.demo.util

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.annotation.PluralsRes

object ViewUtility {
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun setProperty(label: TextView, valueView: TextView, value: Any?) {
        setVisibility(label, value != null)
        setVisibility(valueView, value != null)
        valueView.text = value.toString()
    }

    fun toggleVisibilityPairs(trueView: View, falseView: View, value: Boolean) {
        setVisibility(trueView, value)
        setVisibility(falseView, !value)
    }

    fun displayPluralText(resources: Resources, @PluralsRes plurals: Int, numValue: Int): String {
        return resources.getQuantityString(plurals, numValue, numValue)
    }
}

package com.tilikki.training.unimager.demo.view.main

import android.os.Bundle
import androidx.activity.compose.setContent
import com.tilikki.training.unimager.demo.ui.theme.AppTheme
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PhotoSearchScreen(viewModel = viewModel)
            }
        }
    }
}
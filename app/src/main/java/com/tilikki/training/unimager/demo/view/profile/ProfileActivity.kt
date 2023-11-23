package com.tilikki.training.unimager.demo.view.profile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.tilikki.training.unimager.demo.ui.theme.AppTheme
import com.tilikki.training.unimager.demo.ui.theme.SimpleScaffold
import com.tilikki.training.unimager.demo.view.compose.ErrorScreen
import com.tilikki.training.unimager.demo.view.compose.LoadingIndicator
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ProfileActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModel: ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "User Profile"

        val username = getFromIntent()
        if (username == null) {
            finish()
        }

        viewModel.fetchUserProfile(username!!)
        setContent {
            val photos by viewModel.userPhotos.observeAsState()
            val user by viewModel.userProfile.observeAsState()
            val fetching by viewModel.isFetching.observeAsState()
            val success by viewModel.successResponse.observeAsState()
            AppTheme {
                SimpleScaffold {
                    if (fetching == true) {
                        LoadingIndicator()
                    } else {
                        if (success?.success == true && user != null) {
                            ProfileView(user = user!!, photos = photos)
                        } else {
                            ErrorScreen()
                        }
                    }
                }
            }

        }
    }

    private fun getFromIntent(): String? {
        return intent.getStringExtra(INTENT_URL)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val INTENT_URL: String = "com.tilikki.training.unimager.demo.Username"
    }
}
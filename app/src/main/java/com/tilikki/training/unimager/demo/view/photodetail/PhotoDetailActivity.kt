package com.tilikki.training.unimager.demo.view.photodetail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.compose.setContent
import com.tilikki.training.unimager.demo.ui.theme.AppTheme
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PhotoDetailActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModel: PhotoDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val photoId = getFromIntent()
        if (photoId == null) {
            finish()
            return
        }

        viewModel.attachPhoto(photoId)
        setContent {
            AppTheme {
                PhotoDetailScreen(viewModel)
            }
        }
    }

    private fun getFromIntent(): String? {
        return intent.getStringExtra(INTENT_URL)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    companion object {
        const val INTENT_URL: String = "com.tilikki.training.unimager.demo.PhotoId"
    }
}

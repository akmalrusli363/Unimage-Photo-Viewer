package com.tilikki.training.unimager.demo.view.photodetail

import android.os.Bundle
import androidx.activity.compose.setContent
import com.tilikki.training.unimager.demo.ui.theme.AppTheme
import com.tilikki.training.unimager.demo.ui.theme.SimpleScaffold
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
                SimpleScaffold {
                    PhotoDetailScreen(viewModel)
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
        const val INTENT_URL: String = "com.tilikki.training.unimager.demo.PhotoId"
    }
}

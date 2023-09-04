package com.tilikki.training.unimager.demo.view.photodetail

import android.os.Bundle
import android.view.MenuItem
import com.tilikki.training.unimager.demo.databinding.ActivityPhotoDetailBinding
import com.tilikki.training.unimager.demo.ui.theme.AppTheme
import com.tilikki.training.unimager.demo.util.ViewUtility
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PhotoDetailActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModel: PhotoDetailViewModel

    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        val photoId = getFromIntent()
        if (photoId == null) {
            finish()
            return
        }

        viewModel.attachPhoto(photoId)
        viewModel.photo.observe(this) {
            binding.composeView.setContent {
                AppTheme() {
                    PhotoDetailView(photo = it)
                }
            }
        }
        viewModel.isFetching.observe(this) {
            ViewUtility.setVisibility(binding.pbLoading, it)
            if (it) {
                ViewUtility.setVisibility(binding.nsvPage, false)
                ViewUtility.setVisibility(binding.llError, false)
            }
        }
        viewModel.successResponse.observe(this) {
            it.observeResponseStatus({
                toggleDataState(true)
            }, {
                toggleDataState(false)
            })
        }
    }

    private fun toggleDataState(success: Boolean) {
        ViewUtility.toggleVisibilityPairs(binding.nsvPage, binding.llError, success)
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

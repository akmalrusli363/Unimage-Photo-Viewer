package com.tilikki.training.unimager.demo.view.photodetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tilikki.training.unimager.demo.core.MyApplication
import com.tilikki.training.unimager.demo.databinding.ActivityPhotoDetailBinding
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.view.viewModel.ViewModelFactory
import javax.inject.Inject

class PhotoDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PhotoDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(PhotoDetailViewModel::class.java)
    }

    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).getMainActivityComponent().inject(this)
        super.onCreate(savedInstanceState)

        val photoUrl = getFromIntent()
        LogUtility.showToast(this, "Image: $photoUrl")
        if (photoUrl == null) {
            finish()
        }

        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        binding.viewModel = viewModel
    }

    private fun getFromIntent(): String? {
        return intent.getStringExtra(INTENT_URL)
    }

    companion object {
        const val INTENT_URL: String = "com.tilikki.training.unimager.demo.PhotoUrl"
    }
}
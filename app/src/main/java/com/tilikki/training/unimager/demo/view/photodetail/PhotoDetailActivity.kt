package com.tilikki.training.unimager.demo.view.photodetail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.core.MyApplication
import com.tilikki.training.unimager.demo.databinding.ActivityPhotoDetailBinding
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.util.ImageLoader
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.view.viewModel.ViewModelFactory
import java.util.*
import javax.inject.Inject

class PhotoDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PhotoDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(PhotoDetailViewModel::class.java)
    }

    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).getUserComponent().inject(this)
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val photoId = getFromIntent()
        LogUtility.showToast(this, "Image: $photoId")
        if (photoId == null) {
            finish()
        }

        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        binding.viewModel = viewModel
        viewModel.attachPhoto(photoId)

        viewModel.photo.observe(this, {
            binding.apply {
                ImageLoader.loadImage(it.imageUrl, ivPhotoImage)
                ivPhotoImage.contentDescription = it.altDescription
                tvLikes.text = it.likes.toString()
                setTextField(llDescription, tvDescription, it.description)
                setTextField(llAltDescription, tvAltDescription, it.altDescription)

                ImageLoader.loadImage(it.user.profileImageUrl, ivProfileImage)
                ivProfileImage.contentDescription = getDisplayFullName(it.user)
                tvUsername.text = it.user.username
                tvFullName.text = it.user.name
                clProfileBox.setOnClickListener { _ ->
                    LogUtility.showToast(
                        this@PhotoDetailActivity,
                        "User ID: ${it.user.id} [@${it.user.username}]"
                    )
                }
            }
        })
    }

    private fun getDisplayFullName(user: User): String {
        return String.format(
            Locale.ROOT, getString(R.string.username_format), user.name, user.username
        )
    }


    private fun getFromIntent(): String? {
        return intent.getStringExtra(INTENT_URL)
    }

    private fun setTextField(viewGroup: ViewGroup, textView: TextView, value: String?) {
        setGroupVisibility(viewGroup, !value.isNullOrEmpty())
        textView.text = value
    }

    private fun setGroupVisibility(view: ViewGroup, value: Boolean) {
        view.visibility = if (value) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    companion object {
        const val INTENT_URL: String = "com.tilikki.training.unimager.demo.PhotoId"
    }
}
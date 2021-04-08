package com.tilikki.training.unimager.demo.view.photodetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.core.MyApplication
import com.tilikki.training.unimager.demo.databinding.ActivityPhotoDetailBinding
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.util.ImageLoader
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.util.ViewUtility
import com.tilikki.training.unimager.demo.view.profile.ProfileActivity
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

        viewModel.attachPhoto(photoId)
        viewModel.photo.observe(this, {
            bindPhotoDetails(it)
        })
        viewModel.isFetching.observe(this, {
            ViewUtility.toggleVisibilityPairs(binding.pbLoading, binding.nsvPage, it)
        })
    }

    private fun bindPhotoDetails(photo: PhotoDetail) {
        binding.apply {
            ImageLoader.loadImage(photo.imageUrl, ivPhotoImage)
            ivPhotoImage.contentDescription = photo.altDescription
            tvLikes.text = photo.likes.toString()
            setTextField(llDescription, tvDescription, photo.description)
            setTextField(llAltDescription, tvAltDescription, photo.altDescription)

            ImageLoader.loadImage(photo.user.profileImageUrl, ivProfileImage)
            ivProfileImage.contentDescription = getDisplayFullName(photo.user)
            tvUsername.text = photo.user.username
            tvFullName.text = photo.user.name
            clProfileBox.setOnClickListener {
                val intent = Intent(this@PhotoDetailActivity, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.INTENT_URL, photo.user.username)
                this@PhotoDetailActivity.startActivity(intent)
            }
        }
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
        ViewUtility.setVisibility(viewGroup, !value.isNullOrEmpty())
        textView.text = value
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
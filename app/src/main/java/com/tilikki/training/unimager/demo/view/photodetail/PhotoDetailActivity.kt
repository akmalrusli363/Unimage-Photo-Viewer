package com.tilikki.training.unimager.demo.view.photodetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.databinding.ActivityPhotoDetailBinding
import com.tilikki.training.unimager.demo.model.ExifDetail
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.util.ImageLoader
import com.tilikki.training.unimager.demo.util.ViewUtility
import com.tilikki.training.unimager.demo.util.formatAsString
import com.tilikki.training.unimager.demo.view.profile.ProfileActivity
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

class PhotoDetailActivity : DaggerAppCompatActivity() {
    companion object {
        const val INTENT_URL: String = "com.tilikki.training.unimager.demo.PhotoId"
    }

    @Inject
    lateinit var viewModel: PhotoDetailViewModel

    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val photoId = getFromIntent()
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
        binding.run {
            ImageLoader.loadImage(photo.previewUrl, ivPhotoImage)
            ivPhotoImage.contentDescription = photo.altDescription
            tvLikes.text = photo.likes.toString()
            setTextField(llDescription, tvDescription, photo.description)
            setTextField(llAltDescription, tvAltDescription, photo.altDescription)
            setTextField(llResolution, tvResolution, getImageResolution(photo))
            setTextField(clPublishedDate, tvPublishedDate, photo.createdAt.formatAsString())
            setExifInformation(photo.exif)

            ImageLoader.loadImage(photo.user.profileImageUrl, ivProfileImage)
            ivProfileImage.contentDescription = getDisplayFullName(photo.user)
            tvUsername.text = photo.user.username
            tvFullName.text = photo.user.name
            clProfileBox.setOnClickListener {
                val intent = Intent(this@PhotoDetailActivity, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.INTENT_URL, photo.user.username)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                this@PhotoDetailActivity.startActivity(intent)
            }

            btnDownload.setOnClickListener {
                visitLink(this@PhotoDetailActivity, Uri.parse(photo.fullSizeUrl))
            }

            btnBrowse.setOnClickListener {
                visitLink(this@PhotoDetailActivity, Uri.parse(photo.htmlUrl))
            }
        }
    }

    private fun getDisplayFullName(user: User): String {
        return getString(
            R.string.username_format, user.name, user.username
        )
    }

    private fun getImageResolution(photo: PhotoDetail): String {
        return getString(
            R.string.image_size_full_format, photo.width, photo.height, photo.getOrientation()
        )
    }

    private fun getFromIntent(): String? {
        return intent.getStringExtra(INTENT_URL)
    }

    private fun setTextField(viewGroup: ViewGroup, textView: TextView, value: String?) {
        ViewUtility.setVisibility(viewGroup, !value.isNullOrEmpty())
        textView.text = value
    }

    private fun visitLink(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        context.startActivity(intent)
    }

    private fun setExifInformation(exifDetail: ExifDetail?) {
        binding.llExif.run {
            ViewUtility.setVisibility(this.root, !(exifDetail == null || exifDetail.isEmpty()))
            exifDetail?.let {
                ViewUtility.setProperty(tvLabelExifAperture, tvExifAperture, it.aperture)
                ViewUtility.setProperty(tvLabelExifBrand, tvExifBrand, it.brand)
                ViewUtility.setProperty(tvLabelExifModel, tvExifModel, it.model)
                ViewUtility.setProperty(
                    tvLabelExifExposureTime,
                    tvExifExposureTime,
                    it.exposureTime
                )
                ViewUtility.setProperty(tvLabelExifIso, tvExifIso, it.iso)
                ViewUtility.setProperty(tvLabelExifFocal, tvExifFocal, it.focalLength)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}

package com.tilikki.training.unimager.demo.view.profile

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.databinding.ActivityProfileBinding
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.util.ImageLoader
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.util.ViewUtility
import com.tilikki.training.unimager.demo.util.value
import com.tilikki.training.unimager.demo.view.photogrid.PhotoGridFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ProfileActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModel: ProfileViewModel

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "User Profile"

        val username = getFromIntent()
        if (username == null) {
            finish()
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        viewModel.fetchUserProfile(username!!)
        viewModel.userProfile.observe(this, {
            bindUserProfile(it)
        })
        viewModel.userPhotos.observe(this, {
            var frag = supportFragmentManager.findFragmentById(binding.fragmentPhotosGrid.id)
                    as PhotoGridFragment?
            if (frag != null && viewModel.updateFragment.value == false) {
                Log.d(LogUtility.LOGGER_FETCH_TAG, "update fragment")
                frag.setPhotoList(it)
            } else {
                Log.d(LogUtility.LOGGER_FETCH_TAG, "replace fragment")
                frag = PhotoGridFragment.newInstance(it, viewModel.pages.value)
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentPhotosGrid.id, frag)
                    .commit()
            }
        })
        viewModel.isFetching.observe(this, {
            if (viewModel.updateFragment.value == true)
                ViewUtility.toggleVisibilityPairs(binding.pbLoading, binding.nsvPage, it)
        })
    }

    private fun bindUserProfile(user: User) {
        binding.apply {
            tvUsername.text = user.username
            tvFullName.text = user.name
            tvPhotos.text = ViewUtility.displayPluralText(
                resources,
                R.plurals.total_photos_format,
                user.totalPhotos,
            )
            tvFollowers.text = ViewUtility.displayPluralText(
                resources,
                R.plurals.followers_format,
                user.followers.value(),
            )
            tvFollowing.text = ViewUtility.displayPluralText(
                resources,
                R.plurals.following_format,
                user.following.value()
            )
            ImageLoader.loadImage(user.profileImageUrl, ivProfileImage)
        }
        this@ProfileActivity.title = "${user.name} Profile"
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
        const val INTENT_URL: String = "com.tilikki.training.unimager.demo.Username"
    }
}

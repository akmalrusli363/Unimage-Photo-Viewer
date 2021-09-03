package com.tilikki.training.unimager.demo.view.profile

import android.os.Bundle
import android.view.MenuItem
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.databinding.ActivityProfileBinding
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.util.ImageLoader
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
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentPhotosGrid.id, PhotoGridFragment.newInstance(it))
                .commit()
        })
        viewModel.isFetching.observe(this, {
            ViewUtility.setVisibility(binding.pbLoading, it)
            if (it) {
                ViewUtility.setVisibility(binding.nsvPage, false)
                ViewUtility.setVisibility(binding.llError, false)
            }
        })
        viewModel.successResponse.observe(this, {
            it.observeResponseStatus({
                toggleDataState(true)
            }, {
                toggleDataState(false)
            })
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
            ivProfileImage.contentDescription = getUserAvatarDescription(user)
        }
        this@ProfileActivity.title = "${user.name} Profile"
    }

    private fun getUserAvatarDescription(user: User): String {
        return getString(R.string.user_avatar_description, user.username)
    }

    private fun getFromIntent(): String? {
        return intent.getStringExtra(INTENT_URL)
    }

    private fun toggleDataState(success: Boolean) {
        ViewUtility.toggleVisibilityPairs(binding.nsvPage, binding.llError, success)
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
package com.tilikki.training.unimager.demo.view.profile

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.core.MyApplication
import com.tilikki.training.unimager.demo.databinding.ActivityProfileBinding
import com.tilikki.training.unimager.demo.util.ImageLoader
import com.tilikki.training.unimager.demo.view.main.PhotoRecyclerViewAdapter
import com.tilikki.training.unimager.demo.view.viewModel.ViewModelFactory
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).getUserComponent().inject(this)
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "User Profile"

        val username = getFromIntent()
        if (username == null) {
            finish()
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.rvPhotosGrid.adapter = PhotoRecyclerViewAdapter()
        binding.rvPhotosGrid.layoutManager = getPhotoGridLayoutManager()
        binding.rvPhotosGrid.setHasFixedSize(true)
        setContentView(binding.root)

        viewModel.fetchUserProfile(username!!)
        viewModel.userProfile.observe(this, {
            binding.apply {
                tvUsername.text = it.username
                tvFullName.text = it.name
                tvPhotos.text = getString(R.string.total_photos_format, it.totalPhotos)
                tvFollowers.text = getString(R.string.followers_format, it.followers)
                tvFollowing.text = getString(R.string.following_format, it.following)
                ImageLoader.loadImage(it.profileImageUrl, ivProfileImage)
            }
            this@ProfileActivity.title = "${it.name} Profile"
        })
        viewModel.userPhotos.observe(this, {
            (binding.rvPhotosGrid.adapter as PhotoRecyclerViewAdapter).submitList(it)
        })
    }

    private fun getFromIntent(): String? {
        return intent.getStringExtra(INTENT_URL)
    }

    private fun getPhotoGridLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        return layoutManager
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
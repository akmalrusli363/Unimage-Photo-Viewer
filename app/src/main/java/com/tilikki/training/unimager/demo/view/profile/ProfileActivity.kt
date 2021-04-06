package com.tilikki.training.unimager.demo.view.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tilikki.training.unimager.demo.core.MyApplication
import com.tilikki.training.unimager.demo.databinding.ActivityProfileBinding
import com.tilikki.training.unimager.demo.util.ImageLoader
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

        val username = getFromIntent()
        if (username == null) {
            finish()
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        viewModel.fetchUserProfile(username!!)
        viewModel.userProfile.observe(this, {
            binding.apply {
                tvUsername.text = it.username
                tvFullName.text = it.name
                tvUserStatistics.text =
                    "${it.totalPhotos} photos | ${it.following} following | ${it.followers} followers"
                ImageLoader.loadImage(it.profileImageUrl, ivProfileImage)
            }
        })
    }

    private fun getFromIntent(): String? {
        return intent.getStringExtra(INTENT_URL)
    }

    companion object {
        const val INTENT_URL: String = "com.tilikki.training.unimager.demo.Username"
    }
}
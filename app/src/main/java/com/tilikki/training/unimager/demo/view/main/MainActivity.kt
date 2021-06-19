package com.tilikki.training.unimager.demo.view.main

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import com.tilikki.training.unimager.demo.databinding.ActivityMainBinding
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.util.ViewUtility
import com.tilikki.training.unimager.demo.view.photogrid.PhotoGridFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        binding.svPhotoSearch.apply {
            this.setOnQueryTextListener(searchListener(this))
        }

        viewModel.successResponse.observe(this, { response ->
            response.observeResponseStatus({
                LogUtility.showToast(this, "Fetch success!")
                toggleDataState(success = true)
            }, {
                LogUtility.showToast(this, it?.localizedMessage ?: "An error occurred")
                toggleDataState(success = false)
            })
        })

        viewModel.photos.observe(this, {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentPhotosGrid.id, PhotoGridFragment.newInstance(it))
                .commit()
        })

        viewModel.isFetching.observe(this, {
            ViewUtility.setVisibility(binding.pbLoading, it)
        })
    }

    private fun toggleDataState(success: Boolean) {
        ViewUtility.toggleVisibilityPairs(binding.fragmentPhotosGrid, binding.llError, success)
    }

    private fun searchListener(searchView: SearchView): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    search(query)
                    searchView.clearFocus()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
    }

    private fun search(query: String) {
        viewModel.searchQuery = query
        Log.d(LogUtility.LOGGER_FETCH_TAG, "Searching... ${viewModel.searchQuery}")
        viewModel.fetchPhotos(query)
    }
}
package com.tilikki.training.unimager.demo.view.main

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tilikki.training.unimager.demo.core.MyApplication
import com.tilikki.training.unimager.demo.databinding.ActivityMainBinding
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.util.ViewUtility
import com.tilikki.training.unimager.demo.view.viewModel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).getUserComponent().inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        binding.rvPhotosGrid.adapter = PhotoRecyclerViewAdapter()
        binding.rvPhotosGrid.layoutManager = PhotoRecyclerViewAdapter.getPhotoGridLayoutManager()
        binding.rvPhotosGrid.setHasFixedSize(true)

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
            (binding.rvPhotosGrid.adapter as PhotoRecyclerViewAdapter).submitList(it)
        })

        viewModel.isFetching.observe(this, {
            ViewUtility.setVisibility(binding.pbLoading, it)
        })
    }

    private fun toggleDataState(success: Boolean) {
        ViewUtility.toggleVisibilityPairs(binding.rvPhotosGrid, binding.llError, success)
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
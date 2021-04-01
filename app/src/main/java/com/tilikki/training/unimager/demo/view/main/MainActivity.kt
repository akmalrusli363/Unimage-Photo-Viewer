package com.tilikki.training.unimager.demo.view.main

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tilikki.training.unimager.demo.core.MyApplication
import com.tilikki.training.unimager.demo.databinding.ActivityMainBinding
import com.tilikki.training.unimager.demo.util.LogUtility
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
        (application as MyApplication).getMainActivityComponent().inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.rvPhotosGrid.adapter = PhotoRecyclerViewAdapter()
        binding.rvPhotosGrid.layoutManager = getPhotoGridLayoutManager()
        binding.rvPhotosGrid.setHasFixedSize(true)

        binding.svPhotoSearch.apply {
            this.setOnQueryTextListener(searchListener(this))
        }

        viewModel.successResponse.observe(this, {
            if (!it.success) {
                if (it.error != null) {
                    Toast.makeText(this, it.error.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fetch success!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchListener(searchView: SearchView) : SearchView.OnQueryTextListener {
        return object: SearchView.OnQueryTextListener {
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

    private fun getPhotoGridLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        return layoutManager
    }
}
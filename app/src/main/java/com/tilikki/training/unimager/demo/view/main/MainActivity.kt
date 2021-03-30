package com.tilikki.training.unimager.demo.view.main

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tilikki.training.unimager.demo.core.MyApplication
import com.tilikki.training.unimager.demo.databinding.ActivityMainBinding
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
                Log.w("Unimager", "While search using physical keyboard, " +
                        "DO NOT PROCEED USING ENTER KEY FROM YOUR KEYBOARD!! " +
                        "Use virtual keyboard 'enter' key instead!")
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
        Log.d("Unimager", "Searching... ${viewModel.searchQuery}")
        viewModel.fetchPhotos(query)
    }
}
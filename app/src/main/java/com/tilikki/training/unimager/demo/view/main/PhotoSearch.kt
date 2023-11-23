package com.tilikki.training.unimager.demo.view.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData
import com.tilikki.training.unimager.demo.view.compose.ErrorScreen
import com.tilikki.training.unimager.demo.view.compose.LoadingIndicator
import com.tilikki.training.unimager.demo.view.compose.PreviewEmptyScreen
import com.tilikki.training.unimager.demo.view.compose.PreviewInitialStateScreen
import com.tilikki.training.unimager.demo.view.compose.SearchBar
import com.tilikki.training.unimager.demo.view.photogrid.PhotoGrid

@Composable
fun PhotoSearchScreen(viewModel: MainViewModel) {
    val query = remember { mutableStateOf("") }
    Column {
        SearchBar(
            query = query.value,
            hint = stringResource(id = R.string.explore_photo),
            onQueryChange = { newQuery ->
                query.value = newQuery
            },
            modifier = Modifier.padding(
                horizontal = SizeUnit.SPACE_LARGE,
                vertical = SizeUnit.SPACE_SMALL
            ),
            onSearch = {
                viewModel.searchQuery = query.value
                Log.d(LogUtility.LOGGER_FETCH_TAG, "Searching... ${viewModel.searchQuery}")
                viewModel.fetchPhotos(query.value)
            }
        )
        PhotoSearchState(viewModel = viewModel)
    }
}

@Composable
fun PhotoSearchState(viewModel: MainViewModel) {
    val photos by viewModel.photos.observeAsState()
    val fetching by viewModel.isFetching.observeAsState()
    val success by viewModel.successResponse.observeAsState()
    val isSearching by viewModel.isSearching.observeAsState()
    if (fetching == true) {
        LoadingIndicator()
    } else {
        if (success?.success == true && photos != null) {
            photos?.let {
                if (it.isNotEmpty()) {
                    PhotoGrid(photos = it)
                } else {
                    PreviewEmptyScreen()
                }
            }
        } else if (isSearching == true) {
            ErrorScreen()
        } else {
            PreviewInitialStateScreen()
        }
    }

}

@Composable
fun PhotoSearchView(photos: List<Photo>, onSearch: (String) -> Unit) {
    val query = remember { mutableStateOf("") }
    Column {
        SearchBar(
            query = query.value,
            hint = stringResource(id = R.string.explore_photo),
            onQueryChange = { newQuery ->
                query.value = newQuery
            },
            modifier = Modifier.padding(
                horizontal = SizeUnit.SPACE_MEDIUM,
                vertical = SizeUnit.SPACE_SMALL
            ),
            onSearch = { onSearch(query.value) }
        )
        PhotoGrid(photos = photos)
    }
}

@Preview
@Composable
fun PreviewPhotoSearchView() {
    val photoList =
        (0..10).map { SampleComposePreviewData.generateSamplePhotoData("photo$it") }
    PhotoSearchView(photos = photoList, onSearch = {})
}
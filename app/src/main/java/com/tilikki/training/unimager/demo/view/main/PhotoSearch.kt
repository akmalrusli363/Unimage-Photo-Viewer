package com.tilikki.training.unimager.demo.view.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.ui.theme.AppTheme
import com.tilikki.training.unimager.demo.ui.theme.SimpleScaffold
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.LogUtility
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData
import com.tilikki.training.unimager.demo.util.getErrors
import com.tilikki.training.unimager.demo.view.compose.ErrorScreen
import com.tilikki.training.unimager.demo.view.compose.LoadingIndicator
import com.tilikki.training.unimager.demo.view.compose.PreviewEmptyScreen
import com.tilikki.training.unimager.demo.view.compose.PreviewInitialStateScreen
import com.tilikki.training.unimager.demo.view.compose.SearchBar
import com.tilikki.training.unimager.demo.view.photogrid.ComposeComponentNames
import com.tilikki.training.unimager.demo.view.photogrid.PagingPhotoGrid
import com.tilikki.training.unimager.demo.view.photogrid.PhotoGrid

@Composable
fun PhotoSearchScreen(viewModel: MainViewModel) {
    val query = viewModel.searchQuery.collectAsState()
    Column {
        SearchBar(
            query = query.value,
            hint = stringResource(id = R.string.explore_photo),
            modifier = Modifier
                .padding(
                    horizontal = SizeUnit.SPACE_LARGE,
                    vertical = SizeUnit.SPACE_SMALL
                )
                .testTag(ComposeComponentNames.PHOTO_SEARCH),
            onSearch = {
                viewModel.triggerSearch(it)
                Log.d(LogUtility.LOGGER_FETCH_TAG, "Searching... $it")
            }
        )
        PhotoSearchState(viewModel = viewModel)
    }
}

@Composable
fun PhotoSearchState(viewModel: MainViewModel) {
    val photoListFlow = remember { viewModel.photoListFlow }
    val photos = photoListFlow.collectAsLazyPagingItems()
    val loadState = photos.loadState
    val isLoading = loadState.refresh is LoadState.Loading
    val errorState = loadState.getErrors()

    if (!viewModel.isSearching) {
        PreviewInitialStateScreen()
    } else if (isLoading) {
        LoadingIndicator()
    } else {
        if (errorState != null) {
            Toast.makeText(
                LocalContext.current,
                "An error occurred! ${errorState.error.message}",
                Toast.LENGTH_SHORT
            ).show()
            ErrorScreen(errorMessage = errorState.error.localizedMessage ?: "An error occurred!")
        } else if (photos.itemCount != 0) {
            PagingPhotoGrid(photos)
        } else {
            PreviewEmptyScreen()
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
            onSearch = { query -> onSearch(query) }
        )
        PhotoGrid(photos = photos)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoSearchView() {
    val photoList =
        (0..10).map { SampleComposePreviewData.generateSamplePhotoData("photo$it") }
    PhotoSearchView(photos = photoList, onSearch = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoSearchViewDarkTheme() {
    val photoList =
        (0..10).map { SampleComposePreviewData.generateSamplePhotoData("photo$it") }
    AppTheme(true) {
        SimpleScaffold {
            PhotoSearchView(photos = photoList, onSearch = {})
        }
    }
}
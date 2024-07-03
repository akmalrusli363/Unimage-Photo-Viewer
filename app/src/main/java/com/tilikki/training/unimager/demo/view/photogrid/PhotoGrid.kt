package com.tilikki.training.unimager.demo.view.photogrid

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData

@Composable
fun PhotoGridWithProfile(
    photos: List<Pair<Photo, User>>,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = SizeUnit.SPACE_LARGE,
        vertical = SizeUnit.SPACE_MEDIUM
    ),
    header: @Composable () -> Unit = {}
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceEvenly,
//        contentPadding = padding,
        modifier = modifier
            .animateContentSize()
            .fillMaxSize()
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            Column {
                header()
            }
        }
        items(photos.size) { photoId ->
            PhotoCardWithUserProfile(
                photo = photos[photoId].first,
                user = photos[photoId].second,
                modifier = Modifier.padding(SizeUnit.SPACE_MEDIUM),
            )
        }
    }
}

@Composable
fun PhotoGrid(photos: List<Photo>, modifier: Modifier = Modifier, header: @Composable () -> Unit = {}) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .animateContentSize()
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = SizeUnit.SPACE_LARGE, vertical = SizeUnit.SPACE_MEDIUM)
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            Column {
                header()
            }
        }
        items(photos.size) { photoId ->
            PhotoCard(
                photo = photos[photoId],
                modifier = Modifier.padding(SizeUnit.SPACE_SMALL),
            )
        }
    }
}


@Preview
@Composable
private fun PreviewPhotoGrid() {
    PhotoGrid(photos = (0..10).map { SampleComposePreviewData.generateSamplePhotoData("photo$it") })
}

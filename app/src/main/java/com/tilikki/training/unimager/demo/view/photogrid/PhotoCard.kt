package com.tilikki.training.unimager.demo.view.photogrid

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivity

@Composable
fun PhotoGrid(photos: List<Photo>, modifier: Modifier = Modifier) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.animateContentSize().fillMaxSize(),
        contentPadding = PaddingValues(SizeUnit.SPACE_LARGE)
    ) {
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
fun PreviewPhotoGrid() {
    PhotoGrid(photos = (0..10).map { SampleComposePreviewData.generateSamplePhotoData("photo$it") })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoCard(
    photo: Photo,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(SizeUnit.CORNER_RADIUS),
        elevation = 4.dp,
        onClick = {
            val intent = Intent(context, PhotoDetailActivity::class.java)
            intent.putExtra(PhotoDetailActivity.INTENT_URL, photo.id)
            context.startActivity(intent)
        }) {
        Box {
            AsyncImage(
                model = photo.thumbnailUrl,
                placeholder = painterResource(id = R.drawable.ic_loading),
                contentDescription = photo.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 80.dp),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Preview
@Composable
fun PreviewPhotoCard() {
    PhotoCard(photo = SampleComposePreviewData.generateSamplePhotoData("lorem"))
}
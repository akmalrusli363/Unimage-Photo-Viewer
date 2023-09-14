package com.tilikki.training.unimager.demo.view.photogrid

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData
import com.tilikki.training.unimager.demo.view.compose.ComposeHelper
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivity

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
                placeholder = ComposeHelper.getCircularProgressBar(),
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
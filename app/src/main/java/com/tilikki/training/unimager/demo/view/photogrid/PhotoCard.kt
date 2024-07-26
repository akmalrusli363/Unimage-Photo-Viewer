package com.tilikki.training.unimager.demo.view.photogrid

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData
import com.tilikki.training.unimager.demo.view.compose.ComposeHelper
import com.tilikki.training.unimager.demo.view.photodetail.PhotoDetailActivity

@Composable
fun PhotoCard(
    photo: Photo,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) = PhotoCardWithUserProfile(
    photo = photo,
    user = null,
    modifier = modifier,
    context = context
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoCardWithUserProfile(
    photo: Photo,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) = PhotoCardWithUserProfile(
    photo = photo,
    user = null,
    modifier = modifier,
    context = context
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoCardWithUserProfile(
    photo: Photo,
    user: User?,
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
            user?.let { userData ->
                SimpleUsernameCardWithAvatar(user = userData)
            }
        }
    }
}

@Composable
fun SimpleUsernameCardWithAvatar(user: User, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(SizeUnit.SPACE_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.profileImageUrl,
            placeholder = painterResource(id = R.drawable.ic_image),
            contentDescription = stringResource(
                R.string.username_format, user.name, user.username
            ),
            modifier = Modifier
                .width(32.dp)
                .aspectRatio(1f)
                .padding(SizeUnit.SPACE_SMALL)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Text(
            "@${user.username}",
            style = MaterialTheme.typography.subtitle2.copy(
                shadow = Shadow(
                    blurRadius = 4f,
                    offset = Offset(2f, 2f)
                ),
            ),
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier
                .padding(start = SizeUnit.SPACE_SMALL)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewPhotoCard() {
    PhotoCard(photo = SampleComposePreviewData.generateSamplePhotoData("lorem"))
}

@Preview
@Composable
private fun PreviewSimpleUsernameCardWithAvatar() {
    SimpleUsernameCardWithAvatar(
        user = SampleComposePreviewData.generateSampleUserData("lorem ipsum")
    )
}

@Preview
@Composable
fun PreviewPhotoCardWithUserProfile() {
    PhotoCardWithUserProfile(
        photo = SampleComposePreviewData.generateSamplePhotoData("lorem"),
        user = SampleComposePreviewData.generateSampleUserData("ipsum"),
    )
}

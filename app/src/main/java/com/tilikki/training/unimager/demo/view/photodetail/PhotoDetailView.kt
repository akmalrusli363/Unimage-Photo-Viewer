package com.tilikki.training.unimager.demo.view.photodetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.model.ExifDetail
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.ui.theme.AppTheme
import com.tilikki.training.unimager.demo.ui.theme.Pink
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData
import com.tilikki.training.unimager.demo.util.formatAsString
import com.tilikki.training.unimager.demo.view.compose.BigParameterField
import com.tilikki.training.unimager.demo.view.compose.ComposeHelper
import com.tilikki.training.unimager.demo.view.compose.ErrorScreen
import com.tilikki.training.unimager.demo.view.compose.HeadingField
import com.tilikki.training.unimager.demo.view.compose.LoadingIndicator
import com.tilikki.training.unimager.demo.view.compose.ParameterField
import com.tilikki.training.unimager.demo.view.compose.ParametricHeadingField
import com.tilikki.training.unimager.demo.view.profile.ProfileActivity

@Composable
fun PhotoDetailScreen(viewModel: PhotoDetailViewModel) {
    val photoDetail by viewModel.photo.observeAsState()
    val fetching by viewModel.isFetching.observeAsState()
    val success by viewModel.successResponse.observeAsState()
    if (fetching == true) {
        LoadingIndicator()
    } else {
        if (success?.success == true && photoDetail != null) {
            photoDetail?.let {
                Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    PhotoDetailView(photo = it)
                }
            }
        } else {
            ErrorScreen()
        }
    }
}

@Composable
fun PhotoDetailView(photo: PhotoDetail) {
    Column {
        //TODO: full screen image
        AsyncImage(
            model = photo.fullSizeUrl,
            placeholder = ComposeHelper.getCircularProgressBar(),
            contentDescription = photo.description,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 80.dp),
            contentScale = ContentScale.FillWidth
        )
        //TODO: like, download, view in web
        PhotoDetailActions(photo)
        //TODO: profile
        ProfileInfo(user = photo.user, modifier = Modifier.padding(SizeUnit.SPACE_SMALL))
        Column(modifier = Modifier.padding(SizeUnit.SPACE_MEDIUM)) {
            //TODO: published date
            BigParameterField(
                field = stringResource(id = R.string.published_date),
                value = photo.createdAt.formatAsString(),
                modifier = Modifier.padding(vertical = SizeUnit.SPACE_MEDIUM)
            )
            //TODO: description
            ParametricHeadingField(
                title = stringResource(id = R.string.description),
                value = photo.description,
                modifier = Modifier.padding(vertical = SizeUnit.SPACE_MEDIUM)
            )
            //TODO: alt description
            ParametricHeadingField(
                title = stringResource(id = R.string.alt_description),
                value = photo.altDescription,
                modifier = Modifier.padding(vertical = SizeUnit.SPACE_MEDIUM)
            )
            //TODO: image resolution
            HeadingField(title = stringResource(id = R.string.image_resolution)) {
                Text(
                    stringResource(
                        id = R.string.image_size_full_format,
                        photo.width,
                        photo.height,
                        photo.getOrientation()
                    )
                )
            }
        }
        //TODO: EXIF info
        if (!(photo.exif == null || photo.exif.isEmpty())) {
            ExifInfo(exif = photo.exif)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPhotoDetailView() {
    AppTheme {
        PhotoDetailView(
            photo = SampleComposePreviewData.createPhotoDetail(
                photo = SampleComposePreviewData.generateSamplePhotoData("photoId"),
                user = SampleComposePreviewData.generateSampleUserData()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPhotoDetailViewWithExif() {
    PhotoDetailView(
        photo = SampleComposePreviewData.createPhotoDetail(
            photo = SampleComposePreviewData.generateSamplePhotoData("photoId"),
            user = SampleComposePreviewData.generateSampleUserData(),
            exif = ExifDetail("SONI", "Soni A48", "1/10000", 1.1f, 69.0f, 100)
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPhotoDetailViewWithExifDarkTheme() {
    AppTheme(true) {
        SimpleScaffold {
            PhotoDetailView(
                photo = SampleComposePreviewData.createPhotoDetail(
                    photo = SampleComposePreviewData.generateSamplePhotoData("photoId"),
                    user = SampleComposePreviewData.generateSampleUserData(),
                    exif = ExifDetail("SONI", "Soni A48", "1/10000", 1.1f, 69.0f, 100)
                )
            )
        }
    }
}

@Composable
fun PhotoDetailActions(photo: PhotoDetail, context: Context = LocalContext.current) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(SizeUnit.SPACE_MEDIUM)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_favorite),
            tint = Pink,
            contentDescription = null,
            modifier = Modifier.padding(SizeUnit.SPACE_SMALL)
        )
        Text(
            photo.likes.toString(),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(SizeUnit.SPACE_SMALL)
        )
        Spacer(Modifier.weight(1f))
        Button(
            onClick = { ComposeHelper.visitLink(context, Uri.parse(photo.fullSizeUrl)) },
            modifier = Modifier
                .padding(SizeUnit.SPACE_SMALL)
                .size(40.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = SizeUnit.SPACE_SMALL),
            colors = ButtonDefaults.outlinedButtonColors()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = stringResource(id = R.string.download)
            )
        }
        Button(
            onClick = { ComposeHelper.visitLink(context, Uri.parse(photo.htmlUrl)) },
            modifier = Modifier
                .padding(SizeUnit.SPACE_SMALL)
                .height(40.dp)
        ) {
            Text(stringResource(id = R.string.view_in_web).uppercase())
        }
    }
}

@Preview(widthDp = 360)
@Composable
private fun PreviewPhotoDetailActions() {
    PhotoDetailActions(
        photo = SampleComposePreviewData.createPhotoDetail(
            photo = SampleComposePreviewData.generateSamplePhotoData("photoId"),
            user = SampleComposePreviewData.generateSampleUserData()
        )
    )
}

@Composable
fun ExifInfo(exif: ExifDetail) {
    HeadingField(title = stringResource(id = R.string.exif), modifier = Modifier.padding(SizeUnit.SPACE_MEDIUM)) {
        Column {
            ParameterField(fieldRes = R.string.exif_brand, value = exif.brand)
            ParameterField(fieldRes = R.string.exif_model, value = exif.model)
            ParameterField(fieldRes = R.string.exif_exposure_time, value = exif.exposureTime)
            ParameterField(fieldRes = R.string.exif_aperture, value = exif.aperture?.let { "f/$it" })
            ParameterField(fieldRes = R.string.exif_focal_length, value = exif.focalLength?.let { "$it mm" })
            ParameterField(fieldRes = R.string.exif_iso, value = exif.iso)
        }
    }
}

@Preview
@Composable
private fun PreviewExifInfo() {
    ExifInfo(ExifDetail("SONI", "Soni A48", "1/10000", 1.1f, 69.0f, 100))
}

@Composable
fun ProfileInfo(
    user: User,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.INTENT_URL, user.username)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                context.startActivity(intent)
            }
            .padding(SizeUnit.SPACE_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.profileImageUrl,
            placeholder = ComposeHelper.getCircularProgressBar(),
            contentDescription = stringResource(
                R.string.username_format, user.name, user.username
            ),
            modifier = Modifier
                .width(64.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        )
        Column(Modifier.padding(start = SizeUnit.SPACE_LARGE)) {
            Text(
                user.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = SizeUnit.SPACE_SMALL)
            )
            Text(
                user.username,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProfileInfo() {
    ProfileInfo(User("abz", "@username", "my username is long", "", "", "", "", 0, 0, 0))
}
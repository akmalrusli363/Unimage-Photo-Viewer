package com.tilikki.training.unimager.demo.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.ui.theme.AppTheme
import com.tilikki.training.unimager.demo.ui.theme.SimpleScaffold
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.SampleComposePreviewData
import com.tilikki.training.unimager.demo.util.value
import com.tilikki.training.unimager.demo.view.compose.PreviewEmptyScreen
import com.tilikki.training.unimager.demo.view.photogrid.ComposeComponentNames
import com.tilikki.training.unimager.demo.view.photogrid.PhotoGrid

@Composable
fun ProfileView(user: User, photos: List<Photo>?) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (!photos.isNullOrEmpty()) {
            PhotoGrid(photos, Modifier.testTag(ComposeComponentNames.PROFILE_PHOTO_GRID)) {
                UserOverview(user = user)
                Divider(modifier = Modifier.padding(SizeUnit.SPACE_SMALL), color = Color.Gray)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(16.dp)
                )
            }
        } else {
            UserOverview(user = user)
            Divider(modifier = Modifier.padding(SizeUnit.SPACE_SMALL), color = Color.Gray)
            PreviewEmptyScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileView() {
    val user = SampleComposePreviewData.generateSampleUserData("username")
    val photos = (0..10).map { SampleComposePreviewData.generateSamplePhotoData("photo$it") }
    ProfileView(user = user, photos = photos)
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileViewDarkTheme() {
    val user = SampleComposePreviewData.generateSampleUserData("username")
    val photos = (0..10).map { SampleComposePreviewData.generateSamplePhotoData("photo$it") }
    AppTheme(true) {
        SimpleScaffold {
            ProfileView(user = user, photos = photos)
        }
    }
}

@Composable
fun UserOverview(user: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(SizeUnit.SPACE_MEDIUM)
            .fillMaxWidth()
    ) {
        val fullNameField = stringResource(id = R.string.account_full_name)
        val usernameField = stringResource(id = R.string.account_username)
        AsyncImage(
            model = user.profileImageUrl,
            placeholder = painterResource(id = R.drawable.ic_person),
            contentDescription = stringResource(
                R.string.username_avatar_format, user.name, user.username
            ),
            modifier = Modifier
                .padding(SizeUnit.SPACE_SMALL)
                .width(64.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        )
        Text(
            user.name,
            modifier = Modifier.semantics { contentDescription = fullNameField },
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
        )
        Text(
            user.username,
            modifier = Modifier.semantics { contentDescription = usernameField },
            style = MaterialTheme.typography.subtitle1
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                Text(
                    pluralStringResource(
                        id = R.plurals.total_photos_format,
                        count = user.totalPhotos, user.totalPhotos
                    ),
                    modifier = Modifier.padding(SizeUnit.SPACE_MEDIUM),
                )
                Text(
                    pluralStringResource(
                        id = R.plurals.following_format,
                        count = user.following.value(), user.following.value()
                    ), modifier = Modifier.padding(SizeUnit.SPACE_MEDIUM)
                )
                Text(
                    pluralStringResource(
                        id = R.plurals.followers_format,
                        count = user.followers.value(), user.followers.value()
                    ), modifier = Modifier.padding(SizeUnit.SPACE_MEDIUM)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewUserOverview() {
    UserOverview(user = SampleComposePreviewData.generateSampleUserData("username"))
}


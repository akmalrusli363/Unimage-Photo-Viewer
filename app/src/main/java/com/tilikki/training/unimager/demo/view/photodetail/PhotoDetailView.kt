package com.tilikki.training.unimager.demo.view.photodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.model.ExifDetail
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.util.formatAsString
import com.tilikki.training.unimager.demo.view.compose.BigParameterField
import com.tilikki.training.unimager.demo.view.compose.HeadingField
import com.tilikki.training.unimager.demo.view.compose.ParameterField
import com.tilikki.training.unimager.demo.view.compose.ParametricHeadingField

@Composable
fun PhotoDetailView(photo: PhotoDetail) {
    Column {
        //TODO: full screen image
        AsyncImage(
            model = photo.fullSizeUrl,
            placeholder = ColorPainter(Color.Gray),
            contentDescription = photo.description,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .defaultMinSize(minHeight = 80.dp)
                .padding(SizeUnit.SPACE_SMALL),
            contentScale = ContentScale.FillWidth
        )
        //TODO: like, download, view in web
        //TODO: profile
ProfileInfo(user = User("abz", "@username", "my username is long", "", "", "", "", 0, 0, 0))
        //TODO: published date
        BigParameterField(field = "Published Date", value = photo.createdAt.formatAsString())
        //TODO: description
        ParametricHeadingField(title = "Description", value = photo.description)
        //TODO: alt description
        ParametricHeadingField(title = "Alt Description", value = photo.altDescription)
        //TODO: image resolution
        HeadingField(title = "Image Resolution") {
            Text("${photo.width} x ${photo.height} pixels (${photo.getOrientation()})")
        }
        //TODO: EXIF info
        if (!(photo.exif == null || photo.exif.isEmpty())) {
            ExifInfo(exif = photo.exif)
        }
    }
}

@Composable
fun ExifInfo(exif: ExifDetail) {
    HeadingField(title = "EXIF Info", modifier = Modifier.padding(SizeUnit.SPACE_SMALL)) {
        ParameterField(field = "Brand", value = exif.brand)
        ParameterField(field = "Model", value = exif.model)
        ParameterField(field = "Exposure Time", value = exif.exposureTime)
        ParameterField(field = "Aperture", value = "f/${exif.aperture}")
        ParameterField(field = "Focal Length", value = "${exif.focalLength} mm")
        ParameterField(field = "ISO", value = exif.iso.toString())
    }
}

@Preview
@Composable
fun PreviewExifInfo() {
    ExifInfo(ExifDetail("SONI", "Soni A48", "1/10000", 1.1f, 69.0f, 100))
}

@Composable
fun ProfileInfo(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SizeUnit.SPACE_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "${user.name} (${user.username})",
            modifier = Modifier
                .width(64.dp)
                .aspectRatio(1f)
                .background(Color.DarkGray)
        )
        Column(Modifier.padding(start = SizeUnit.SPACE_LARGE)) {
            Text(
                user.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = SizeUnit.SPACE_SMALL)
            )
            Text(user.username)
        }
    }
}

@Preview
@Composable
fun PreviewProfileInfo() {
    ProfileInfo(User("abz", "@username", "my username is long", "", "", "", "", 0, 0, 0))
}
package com.tilikki.training.unimager.demo.view.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tilikki.training.unimager.demo.R
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit
import com.tilikki.training.unimager.demo.view.photogrid.ComposeComponentNames

@Composable
fun ErrorScreen(
    icon: Painter = painterResource(id = R.drawable.ic_general_error),
    errorMessage: String = stringResource(id = R.string.error_occurred),
    elementId: String = ComposeComponentNames.ERROR_STATE_SCREEN,
) {
    Column(
        Modifier
            .fillMaxSize()
            .testTag(elementId),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.padding(SizeUnit.SPACE_SMALL))
        Text(errorMessage, style = MaterialTheme.typography.h6)
    }
}

@Preview
@Composable
fun PreviewErrorScreen() {
    ErrorScreen(
        painterResource(id = R.drawable.ic_general_error),
        stringResource(id = R.string.error_occurred),
        ComposeComponentNames.ERROR_STATE_SCREEN
    )
}

@Preview
@Composable
fun PreviewEmptyScreen() {
    ErrorScreen(
        painterResource(id = R.drawable.ic_image),
        stringResource(id = R.string.no_photos),
        ComposeComponentNames.EMPTY_PHOTOS_SCREEN
    )
}

@Preview
@Composable
fun PreviewInitialStateScreen() {
    ErrorScreen(
        painterResource(id = R.drawable.ic_launcher_foreground),
        "Search photos",
        ComposeComponentNames.INITIAL_STATE_SCREEN
    )
}
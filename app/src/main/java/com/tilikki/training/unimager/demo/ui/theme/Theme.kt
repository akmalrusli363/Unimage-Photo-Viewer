package com.tilikki.training.unimager.demo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = Yellow400,
    primaryVariant = Orange700,
    secondary = PurpleA400,
    background = DarkBlack
)

private val LightColorPalette = lightColors(
    primary = Orange600,
    primaryVariant = Orange700,
    secondary = PurpleA400

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MoviPediaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
private fun PreviewDarkModeTheme() {
    PreviewElement(true)
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
private fun PreviewLightModeTheme() {
    PreviewElement(false)
}

@Composable
private fun PreviewElement(darkMode: Boolean) {
    MoviPediaTheme(darkTheme = darkMode) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Preview") }
                )
            },
        ) {
            Column(modifier = Modifier
                .padding(it)
                .padding(16.dp)) {
                CircularProgressIndicator()
                Text(text = "title", style = MaterialTheme.typography.h4)
                Checkbox(checked = true, onCheckedChange = {})
                Text(text = "Agree")
                Slider(value = 0.1f, onValueChange = {})
                TextField(value = "hint", onValueChange = {})
                Button(onClick = { }) {
                    Text(text = "I agree")
                }
            }
        }
    }
}
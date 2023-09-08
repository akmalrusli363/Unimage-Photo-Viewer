package com.tilikki.training.unimager.demo.view.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tilikki.training.unimager.demo.ui.theme.SizeUnit

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun HeadingField(
    title: String,
    modifier: Modifier = Modifier,
    innerModifier: Modifier = Modifier,
    contents: @Composable () -> Unit
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = title,
            modifier = Modifier.padding(SizeUnit.SPACE_SMALL),
            style = MaterialTheme.typography.h6
        )
        Box(modifier = innerModifier.padding(SizeUnit.SPACE_SMALL)) {
            contents()
        }
    }
}

@Composable
fun ParametricHeadingField(
    title: String,
    modifier: Modifier = Modifier,
    innerModifier: Modifier = Modifier,
    value: String?
) {
    if (!value.isNullOrBlank()) {
        HeadingField(title, modifier, innerModifier) {
            Text(value)
        }
    }
}

@Composable
fun BigParameterField(field: String, value: String?, modifier: Modifier = Modifier) {
    Row(modifier) {
        Text(
            text = field,
            fontSize = 18.sp,
            modifier = Modifier.padding(SizeUnit.SPACE_SMALL),
            style = MaterialTheme.typography.h6
        )
        Text(
            text = value.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeUnit.SPACE_SMALL),
            textAlign = TextAlign.End,
        )
    }
}

@Composable
fun ParameterField(@StringRes fieldRes: Int, value: Any?) {
    ParameterField(field = stringResource(id = fieldRes), value = value)
}

@Composable
fun ParameterField(field: String, value: Any?) {
    if (value != null && value.toString().isNotBlank()) {
        Row {
            Text(
                text = field,
                modifier = Modifier.padding(SizeUnit.SPACE_SMALL),
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.body1
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = value.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SizeUnit.SPACE_SMALL),
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Preview
@Composable
fun SampleHeadingField() {
    Column(Modifier.fillMaxWidth()) {
        HeadingField("field1") {
            Text(
                text = "lorem ipsum dolor sit amet",
                style = MaterialTheme.typography.body2,
            )

        }
        HeadingField("field2") {
            ParameterField("field1", "testing")
            ParameterField("field2", "lorem ipsum")
        }
    }
}

@Preview
@Composable
fun SampleBigParameterField() {
    BigParameterField("field1", "testing 06/09/1942")
}

@Preview
@Composable
fun SampleParameterField() {
    Column(Modifier.fillMaxWidth()) {
        ParameterField("field1", "testing")
        ParameterField("field2", "lorem ipsum")
        ParameterField("field3", "dolor sit amet")
    }
}

@Preview
@Composable
fun SampleParameterField2() {
    Column(Modifier.fillMaxWidth()) {
        ParameterField("field1", "testing")
        ParameterField("field3", "dolor sit amet")
    }
}
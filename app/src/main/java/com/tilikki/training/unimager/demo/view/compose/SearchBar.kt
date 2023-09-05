package com.tilikki.training.unimager.demo.view.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    hint: String = "Search...",
) {
    TextField(
        value = query,
        onValueChange = { newQuery ->
            onQueryChange(newQuery)
        },
        placeholder = {
            Text(hint)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        )
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    val query = remember { mutableStateOf("") }
    SearchBar(
        query = query.value,
        onQueryChange = { newQuery ->
            query.value = newQuery
        },
        onSearch = {
            // Handle search action here
        }
    )
}
package com.example.unsplashcompose.ui.screen.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchTopBar(
    query: String,
    onValueChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onRemoveClicked: () -> Unit
) {

    Surface(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .semantics {
                contentDescription = "SearchWidget"
            },
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            value = query,
            onValueChange = onValueChanged,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodySmall.copy(
                color = Color.White,
                fontSize = 18.sp
            ),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search here...",
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    color = Color.White
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {onSearchClicked(query)},
                ) {
                    Icon(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions {
                onSearchClicked(query)
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (query.isNotBlank()) {
                            onValueChanged("")
                        } else {
                            onRemoveClicked()
                        }
                    },
                ) {
                    Icon(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun SearchPrev() {
    SearchTopBar(query = "asd", onValueChanged = {}, onSearchClicked = { /*TODO*/ }) {

    }
}
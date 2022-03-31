package com.example.unsplashcompose.ui.screen.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.unsplashcompose.ui.screen.search.component.SearchTopBar
import com.example.unsplashcompose.ui.util.ImageList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterial3Api
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query = viewModel.query.value
    val items = viewModel.state.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                query = query,
                onValueChanged = viewModel::onQueryChanged,
                onSearchClicked = viewModel::getSearchedImages,
            ) {
                navigator.popBackStack()
            }
        }
    ) {
        ImageList(items = items)
    }

}
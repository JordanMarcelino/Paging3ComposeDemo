package com.example.unsplashcompose.ui.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.unsplashcompose.ui.screen.destinations.SearchScreenDestination
import com.example.unsplashcompose.ui.screen.home.component.HomeTopBar
import com.example.unsplashcompose.ui.util.ImageList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterial3Api
@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val items = viewModel.state.value.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar {
                navigator.navigate(SearchScreenDestination())
            }
        },
    ) {
        ImageList(items = items)
    }

}
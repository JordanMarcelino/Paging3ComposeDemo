package com.example.unsplashcompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.domain.usecase.GetImages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getImages: GetImages
) : ViewModel() {

    private val _state = mutableStateOf(getImages())
    val state: State<Flow<PagingData<UnsplashImage>>> = _state
}
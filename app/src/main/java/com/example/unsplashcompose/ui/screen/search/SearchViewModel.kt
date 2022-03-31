package com.example.unsplashcompose.ui.screen.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.domain.usecase.SearchImages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchImages: SearchImages
) : ViewModel() {

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    private val _state = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val state: StateFlow<PagingData<UnsplashImage>> = _state


    fun onQueryChanged(query: String) {
        _query.value = query
    }

    fun getSearchedImages(query: String) = viewModelScope.launch {
        searchImages(query).cachedIn(viewModelScope).collect {
            _state.value = it
        }
    }
}
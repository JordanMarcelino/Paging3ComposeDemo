package com.example.unsplashcompose.domain.usecase

import androidx.paging.PagingData
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchImages @Inject constructor(private val repository: UnsplashRepository) {

    operator fun invoke(query : String) : Flow<PagingData<UnsplashImage>>{
        return repository.getSearchedImage(query)
    }
}
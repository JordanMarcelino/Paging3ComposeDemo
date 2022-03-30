package com.example.unsplashcompose.domain.usecase

import androidx.paging.PagingData
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImages @Inject constructor(private val repository: UnsplashRepository) {

    operator fun invoke() : Flow<PagingData<UnsplashImage>>{
        return repository.getImages()
    }
}
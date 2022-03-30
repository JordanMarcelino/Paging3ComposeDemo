package com.example.unsplashcompose.domain.repository

import androidx.paging.PagingData
import com.example.unsplashcompose.data.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface UnsplashRepository {

    fun getImages() : Flow<PagingData<UnsplashImage>>
}
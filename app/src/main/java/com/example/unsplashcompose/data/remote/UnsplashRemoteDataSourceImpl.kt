package com.example.unsplashcompose.data.remote

import com.example.unsplashcompose.data.model.SearchImageResult
import com.example.unsplashcompose.data.model.UnsplashImage

class UnsplashRemoteDataSourceImpl(
    private val unsplashApi: UnsplashApi
) : UnsplashRemoteDataSource{

    override suspend fun getImages(page: Int): List<UnsplashImage> = unsplashApi.getImages(page)

    override suspend fun getSearchedImage(query: String, page : Int): SearchImageResult = unsplashApi.getSearchedImage(query, page)
}
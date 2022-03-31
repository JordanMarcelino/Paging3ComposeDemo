package com.example.unsplashcompose.data.remote

import com.example.unsplashcompose.data.model.SearchImageResult
import com.example.unsplashcompose.data.model.UnsplashImage

interface UnsplashRemoteDataSource {

    suspend fun getImages(page : Int) : List<UnsplashImage>
    suspend fun getSearchedImage(query : String, page : Int) : SearchImageResult
}
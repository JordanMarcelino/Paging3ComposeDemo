package com.example.unsplashcompose.data.remote

import com.example.unsplashcompose.BuildConfig
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.util.Constant.PER_PAGE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface UnsplashApi {

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("photos")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = PER_PAGE,
    ): List<UnsplashImage>


}
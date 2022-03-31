package com.example.unsplashcompose.data.model

import com.google.gson.annotations.SerializedName

data class SearchImageResult(
    @SerializedName("results")
    val image : List<UnsplashImage>
)

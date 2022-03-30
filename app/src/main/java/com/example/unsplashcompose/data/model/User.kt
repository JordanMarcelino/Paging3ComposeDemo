package com.example.unsplashcompose.data.model


import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String?,
    @Embedded
    @SerializedName("links")
    val userLinks: UserLinks?
)
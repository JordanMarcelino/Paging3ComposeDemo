package com.example.unsplashcompose.data.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.unsplashcompose.util.Constant.UNSPLASH_IMAGE_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImage(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: String?,
    @SerializedName("likes")
    val likes: Int?,
    @Embedded
    @SerializedName("urls")
    val urls: Urls?,
    @Embedded
    @SerializedName("user")
    val user: User?,
){
    fun toRemoteKeys(prevPage : Int?, nextPage : Int?) : UnsplashRemoteKeys = UnsplashRemoteKeys(
        id = id,
        prevPage = prevPage,
        nextPage = nextPage
    )
}
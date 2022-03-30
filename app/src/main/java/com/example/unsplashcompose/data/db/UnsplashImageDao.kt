package com.example.unsplashcompose.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.util.Constant.UNSPLASH_IMAGE_TABLE

@Dao
interface UnsplashImageDao {

    @Query("SELECT * FROM $UNSPLASH_IMAGE_TABLE")
    fun getImages() : PagingSource<Int, UnsplashImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images : List<UnsplashImage>)

    @Query("DELETE FROM $UNSPLASH_IMAGE_TABLE")
    suspend fun deleteImages()
}
package com.example.unsplashcompose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.data.model.UnsplashRemoteKeys

@Database(
    entities = [UnsplashImage::class, UnsplashRemoteKeys::class],
    exportSchema = false,
    version = 1
)
abstract class UnsplashDatabase : RoomDatabase(){
    abstract fun getUnsplashImageDao() : UnsplashImageDao
    abstract fun getUnsplashRemoteKeysDao() : UnsplashRemoteKeysDao
}
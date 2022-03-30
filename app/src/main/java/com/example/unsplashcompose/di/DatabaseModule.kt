package com.example.unsplashcompose.di

import android.app.Application
import androidx.room.Room
import com.example.unsplashcompose.data.db.UnsplashDatabase
import com.example.unsplashcompose.data.db.UnsplashImageDao
import com.example.unsplashcompose.data.db.UnsplashRemoteKeysDao
import com.example.unsplashcompose.util.Constant.UNSPLASH_DATABASE
import dagger.Provides
import javax.inject.Singleton

object DatabaseModule {

    @Provides
    @Singleton
    fun providesUnsplashDatabase(
        application: Application
    ): UnsplashDatabase = Room.databaseBuilder(
        application,
        UnsplashDatabase::class.java,
        UNSPLASH_DATABASE
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesUnsplashImageDao(database: UnsplashDatabase): UnsplashImageDao =
        database.getUnsplashImageDao()

    @Provides
    @Singleton
    fun providesUnsplashRemoteKeysDao(database: UnsplashDatabase): UnsplashRemoteKeysDao =
        database.getUnsplashRemoteKeysDao()

}
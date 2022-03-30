package com.example.unsplashcompose.di

import com.example.unsplashcompose.data.remote.UnsplashApi
import com.example.unsplashcompose.data.remote.UnsplashRemoteDataSource
import com.example.unsplashcompose.data.remote.UnsplashRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun providesUnsplashRemoteDataSource(
        unsplashApi: UnsplashApi
    ): UnsplashRemoteDataSource = UnsplashRemoteDataSourceImpl(unsplashApi)
}
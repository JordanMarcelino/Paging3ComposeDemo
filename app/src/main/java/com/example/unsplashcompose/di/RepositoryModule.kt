package com.example.unsplashcompose.di

import androidx.paging.ExperimentalPagingApi
import com.example.unsplashcompose.data.db.UnsplashDatabase
import com.example.unsplashcompose.data.remote.UnsplashRemoteDataSource
import com.example.unsplashcompose.data.repository.UnsplashRepositoryImpl
import com.example.unsplashcompose.domain.repository.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesUnsplashRepository(
        remoteDataSource: UnsplashRemoteDataSource,
        database: UnsplashDatabase
    ) : UnsplashRepository = UnsplashRepositoryImpl(
        remoteDataSource, database
    )
}
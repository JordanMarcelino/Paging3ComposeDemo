package com.example.unsplashcompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.unsplashcompose.data.db.UnsplashDatabase
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.data.paging.UnsplashRemoteMediator
import com.example.unsplashcompose.data.remote.UnsplashRemoteDataSource
import com.example.unsplashcompose.domain.repository.UnsplashRepository
import com.example.unsplashcompose.util.Constant.PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class UnsplashRepositoryImpl @Inject constructor(
    private val remoteDataSource: UnsplashRemoteDataSource,
    private val unsplashDatabase: UnsplashDatabase
) : UnsplashRepository {


    override fun getImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = { unsplashDatabase.getUnsplashImageDao().getImages() }

        return Pager(
            config = PagingConfig(pageSize = PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(remoteDataSource, unsplashDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
package com.example.unsplashcompose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.unsplashcompose.data.db.UnsplashDatabase
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.data.model.UnsplashRemoteKeys
import com.example.unsplashcompose.data.remote.UnsplashRemoteDataSource
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val remoteDataSource: UnsplashRemoteDataSource,
    private val unsplashDatabase: UnsplashDatabase
) : RemoteMediator<Int, UnsplashImage>() {

    private val unsplashImageDao = unsplashDatabase.getUnsplashImageDao()
    private val unsplashRemoteKeysDao = unsplashDatabase.getUnsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImage>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val keys = getRemoteKeyClosestToCurrentPosition(state)
                    keys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    nextPage
                }
            }

            val response = remoteDataSource.getImages(currentPage)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1


            unsplashDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDao.deleteImages()
                    unsplashRemoteKeysDao.deleteRemoteKeys()
                }

                unsplashImageDao.addImages(response)
                val keys = response.map { image ->
                    image.toRemoteKeys(
                        prevPage, nextPage
                    )
                }
                unsplashRemoteKeysDao.addRemoteKeys(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UnsplashImage>
    ): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UnsplashImage>
    ): UnsplashRemoteKeys? {
        return state.firstItemOrNull()?.let { image ->
            image.id.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UnsplashImage>
    ): UnsplashRemoteKeys? {
        return state.lastItemOrNull()?.let { image ->
            image.id.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }
}
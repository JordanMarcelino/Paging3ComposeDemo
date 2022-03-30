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
                    val prevPage = remoteKeys?.let {
                        it.prevPage ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    }

                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.let {
                        it.nextPage ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    }
                    nextPage
                }
            }

            val response = currentPage?.let { remoteDataSource.getImages(it) }
            val endOfPaginationReached = response?.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage?.minus(1)
            val nextPage = if (endOfPaginationReached == true) null else currentPage?.plus(1)


            unsplashDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDao.deleteImages()
                    unsplashRemoteKeysDao.deleteRemoteKeys()
                }

                response?.let { unsplashImageDao.addImages(it) }
                val keys = response?.let { list ->
                    list.map { image ->
                        image.toRemoteKeys(
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                }
                keys?.let { unsplashRemoteKeysDao.addRemoteKeys(it) }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (e: Exception) {
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
            image.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UnsplashImage>
    ): UnsplashRemoteKeys? {
        return state.lastItemOrNull()?.let { image ->
            image.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }
}
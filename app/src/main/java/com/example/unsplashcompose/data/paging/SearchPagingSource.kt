package com.example.unsplashcompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.data.remote.UnsplashRemoteDataSource
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val remoteDataSource: UnsplashRemoteDataSource,
    private val query: String
) : PagingSource<Int, UnsplashImage>() {

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: 1
        return try {
            val response = remoteDataSource.getSearchedImage(query, currentPage)
            val endOfPaginationReached = response.image.isEmpty()
            if (response.image.isNotEmpty()) {
                LoadResult.Page(
                    data = response.image,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
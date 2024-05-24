package com.example.searchimagesplash.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

abstract class AbsPagingDataSource<T : Any>(
    private val query: String
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }

    abstract suspend fun getSearchResultList(query: String, page: Int, perPage: Int): List<T>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val resultSearch = getSearchResultList(query = query, page = position, perPage = params.loadSize)
            val nextKey = if (resultSearch.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = resultSearch,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val UNSPLASH_STARTING_PAGE_INDEX = 1
        const val NETWORK_PAGE_SIZE = 30
    }
}
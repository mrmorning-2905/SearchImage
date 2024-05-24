package com.example.searchimagesplash.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchimagesplash.data.local.CollectionItem
import com.example.searchimagesplash.data.local.PhotoItem
import com.example.searchimagesplash.data.service.UnSplashApiService
import kotlinx.coroutines.flow.Flow

class UnSplashSearchRepository(private val unSplashApiService: UnSplashApiService) {

    fun getSearchPhotoResultStream(query: String): Flow<PagingData<PhotoItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = AbsPagingDataSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UnSplashPagingPhotosSource(
                    unSplashApiService = unSplashApiService,
                    query = query
                )
            }
        ).flow
    }

    fun getSearchCollectionsResultStream(query: String): Flow<PagingData<CollectionItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = AbsPagingDataSource.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UnSplashPagingCollectionsSource(
                    unSplashApiService = unSplashApiService,
                    query = query
                )
            }
        ).flow
    }
}
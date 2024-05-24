package com.example.searchimagesplash.data.service

import com.example.searchimagesplash.data.remote.SearchCollectionResponseItem
import com.example.searchimagesplash.data.remote.SearchPhotoResponseItem
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface UnSplashApiService {
    @GET("search/photos")
    suspend fun getSearchPhotoResult(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchPhotoResponseItem

    @GET("search/collections")
    suspend fun getSearchCollectionResult(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchCollectionResponseItem

    companion object {
        operator fun invoke(retrofit: Retrofit): UnSplashApiService = retrofit.create()
    }
}
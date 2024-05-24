package com.example.searchimagesplash.data

import com.example.searchimagesplash.data.local.CollectionItem
import com.example.searchimagesplash.data.remote.SearchCollectionResponseItem
import com.example.searchimagesplash.data.service.UnSplashApiService

class UnSplashPagingCollectionsSource(
    private val unSplashApiService: UnSplashApiService,
    query: String
) : AbsPagingDataSource<CollectionItem>(query) {

    override suspend fun getSearchResultList(query: String, page: Int, perPage: Int): List<CollectionItem> {
        val searchResult = unSplashApiService.getSearchCollectionResult(query, page, perPage)
        return searchResult.results.map { it.toCollectionItem() }
    }
}

private fun SearchCollectionResponseItem.Result.toCollectionItem(): CollectionItem {
    return CollectionItem(
        id = id,
        userName = user.name,
        userProfileUrl = user.profileImage.medium,
        coverPhotoUrl = coverPhoto.urls.regular,
        coverDescription = title,
        numberImages = totalPhotos,
        userId = user.id
    )
}
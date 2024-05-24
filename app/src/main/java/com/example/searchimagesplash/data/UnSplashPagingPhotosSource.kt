package com.example.searchimagesplash.data

import com.example.searchimagesplash.data.local.PhotoItem
import com.example.searchimagesplash.data.remote.SearchPhotoResponseItem
import com.example.searchimagesplash.data.service.UnSplashApiService

class UnSplashPagingPhotosSource(
    private val unSplashApiService: UnSplashApiService,
    query: String
) : AbsPagingDataSource<PhotoItem>(query) {

    override suspend fun getSearchResultList(query: String, page: Int, perPage: Int): List<PhotoItem> {
        val response = unSplashApiService.getSearchPhotoResult(
            query = query,
            page = page,
            perPage = perPage
        )
        return response.results.map { it.toPhotoItem() }
    }
}

private fun SearchPhotoResponseItem.Result.toPhotoItem(): PhotoItem {
    return PhotoItem(
        id = id,
        userName = user.name,
        userProfileUrl = user.profileImage.medium,
        coverPhotoUrl = urls.regular,
        photoDescription = altDescription ?: "",
        numberLikes = likes,
        userId = user.id
    )
}
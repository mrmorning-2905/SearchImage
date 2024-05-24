package com.example.searchimagesplash.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.searchimagesplash.data.UnSplashSearchRepository
import com.example.searchimagesplash.data.local.CollectionItem
import kotlinx.coroutines.flow.Flow

class SearchCollectionsViewModel(
    private val repository: UnSplashSearchRepository,
    savedStateHandle: SavedStateHandle
) : AbsSearchViewModel<CollectionItem>(savedStateHandle) {

    override fun searchItems(query: String): Flow<PagingData<CollectionItem>> {
        return repository.getSearchCollectionsResultStream(query)
    }
}
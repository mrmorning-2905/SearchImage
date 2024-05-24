package com.example.searchimagesplash.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.searchimagesplash.data.UnSplashSearchRepository
import com.example.searchimagesplash.data.local.PhotoItem
import kotlinx.coroutines.flow.Flow

class SearchPhotoViewModel(
    private val repository: UnSplashSearchRepository,
    savedStateHandle: SavedStateHandle
) : AbsSearchViewModel<PhotoItem>(savedStateHandle) {

    override fun searchItems(query: String): Flow<PagingData<PhotoItem>> {
        return repository.getSearchPhotoResultStream(query)
    }
}
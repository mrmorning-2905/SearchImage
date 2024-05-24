package com.example.searchimagesplash.ui.viewmodels

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.searchimagesplash.data.UnSplashSearchRepository

class SearchViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: UnSplashSearchRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = with(modelClass) {
        when {
            isAssignableFrom(SearchPhotoViewModel::class.java) -> SearchPhotoViewModel(repository, handle)
            isAssignableFrom(SearchCollectionsViewModel::class.java) -> SearchCollectionsViewModel(repository, handle)
            else -> IllegalArgumentException("Unknown ViewModel class")
        }
    } as T
}
package com.example.searchimagesplash.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchimagesplash.ServiceLocator
import com.example.searchimagesplash.data.local.PhotoItem
import com.example.searchimagesplash.databinding.SearchPhotoFragmentLayoutBinding
import com.example.searchimagesplash.ui.viewmodels.SearchPhotoViewModel
import com.example.searchimagesplash.ui.viewmodels.UiAction
import com.example.searchimagesplash.ui.viewmodels.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchPhotoListFragment : BaseFragment<SearchPhotoFragmentLayoutBinding>(SearchPhotoFragmentLayoutBinding::inflate){
    private val photoListAdapter by lazy(LazyThreadSafetyMode.NONE) { SearchPhotoListAdapter(
        requestManager = Glide.with(this@SearchPhotoListFragment)
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this, ServiceLocator.provideViewModelFactory(requireContext(), this))[SearchPhotoViewModel::class.java]
        binding.binState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiAction = viewModel.accept
        )
    }

    private fun SearchPhotoFragmentLayoutBinding.binState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<PhotoItem>>,
        uiAction: (UiAction) -> Unit
    ) {
        recycleViewContainer.adapter = photoListAdapter
        bindSearch(uiState, uiAction)
        bindList(photoListAdapter, uiState, pagingData, uiAction)
    }

    private fun SearchPhotoFragmentLayoutBinding.bindSearch(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        searchPhotos.setOnEditorActionListener {_, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatePhotoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        searchPhotos.setOnKeyListener {_, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatePhotoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

//        searchPhotos.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                if (!s.isNullOrEmpty()) {
//                    updatePhotoListFromInput(onQueryChanged)
//                }
//            }
//
//        })

        lifecycleScope.launch {
            uiState.map { it.query }
                .distinctUntilChanged()
                .collect(searchPhotos::setText)
        }
    }

    private fun SearchPhotoFragmentLayoutBinding.updatePhotoListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        searchPhotos.text.trim().let {
            if (it.isNotEmpty()) {
                recycleViewContainer.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }

    private fun SearchPhotoFragmentLayoutBinding.bindList(
        photoListAdapter: SearchPhotoListAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<PhotoItem>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        recycleViewContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })

        val notLoading = photoListAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrollForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrollForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest ( photoListAdapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) {
                    recycleViewContainer.scrollToPosition(0)
                }
            }
        }

        lifecycleScope.launch {
            photoListAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && photoListAdapter.itemCount == 0
                emptyList.isVisible = isListEmpty
                recycleViewContainer.isVisible = !isListEmpty
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
            }
        }
    }
}
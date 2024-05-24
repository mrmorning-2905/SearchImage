package com.example.searchimagesplash.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.example.searchimagesplash.data.local.PhotoItem
import com.example.searchimagesplash.databinding.CoverPhotoItemBinding

object PHOTO_DIFF_CALLBACK : DiffUtil.ItemCallback<PhotoItem>() {
    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem == newItem
    }

}
class SearchPhotoListAdapter(
    private val requestManager: RequestManager
) : PagingDataAdapter<PhotoItem, PhotoViewHolder>(PHOTO_DIFF_CALLBACK){
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = getItem(position)
        photoItem?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            binding = CoverPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            requestManager = requestManager
        )
    }
}
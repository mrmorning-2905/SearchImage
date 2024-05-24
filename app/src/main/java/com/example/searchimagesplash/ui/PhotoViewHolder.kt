package com.example.searchimagesplash.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.searchimagesplash.data.local.PhotoItem
import com.example.searchimagesplash.databinding.CoverPhotoItemBinding

class PhotoViewHolder(
    private val binding: CoverPhotoItemBinding,
    private val requestManager: RequestManager
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: PhotoItem) {
        binding.run {
            requestManager
                .load(item.coverPhotoUrl)
                .fitCenter()
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(coverPhoto)

            coverTitle.text = item.photoDescription
            coverDetail.text = "${item.numberLikes} Likes"
        }

    }
}
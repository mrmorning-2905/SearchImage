package com.example.searchimagesplash.data.local

data class CollectionItem(
    val id: String,
    val userName: String,
    val userProfileUrl: String,
    val coverPhotoUrl: String,
    val coverDescription: String,
    val numberImages: Int,
    val userId: String
)
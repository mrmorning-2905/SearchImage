package com.example.searchimagesplash.data.local

data class PhotoItem (
    val id: String,
    val userName: String,
    val userProfileUrl: String,
    val coverPhotoUrl: String,
    val photoDescription: String,
    val numberLikes: Int,
    val userId: String
)
package com.morfly.imageviewer.data


data class PhotoSearchResponse(
    val photos: Photos,
    val stat: String
)

data class Photos(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val photo: List<Photo>,
    val total: String
)

data class Photo(
    val farm: Int,
    val id: String,
    val secret: String,
    val server: String,
    val isFamily: Boolean,
    val isFriend: Boolean,
    val isPublic: Boolean,
    val owner: String,
    val title: String
)
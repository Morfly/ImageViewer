package com.morfly.imageviewer.mock

import com.morfly.imageviewer.data.Photo
import com.morfly.imageviewer.data.PhotoSearchResponse
import com.morfly.imageviewer.data.Photos

object TestDataModels {

    val testPhotoSearchResponse: PhotoSearchResponse
        get() = PhotoSearchResponse(
            photos = testPhotos,
            stat = "ok"
        )

    val testEmptyPhotoSearchResponse: PhotoSearchResponse
        get() = PhotoSearchResponse(
            photos = testEmptyPhotos,
            stat = "ok"
        )

    val testPhotos: Photos
        get() = Photos(
            page = 1,
            pages = 502815,
            perPage = 2,
            photo = listOf(testPhoto),
            total = "1005630"
        )

    val testEmptyPhotos: Photos
        get() = Photos(
            page = 1,
            pages = 0,
            perPage = 2,
            photo = emptyList(),
            total = "0"
        )

    val testPhoto: Photo
        get() = Photo(
            id = "49221164563",
            owner = "186005158@N05",
            secret = "33bb115ce1",
            server = "65535",
            farm = 66,
            title = "Sleepy kitten",
            isPublic = true,
            isFriend = false,
            isFamily = false
        )
}
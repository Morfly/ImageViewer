package com.morfly.imageviewer.data

interface FlickrApi {

    fun searchPhotos(query: String, page: Int, perPage: Int): PhotoSearchResponse

    enum class ImageSize(val modifier: String) {
        SMALL("m"),
        LARGE("b")
    }

    companion object {

        fun buildImageUrl(
            farmId: String,
            serverId: String,
            id: String,
            secret: String,
            size: ImageSize = ImageSize.SMALL
        ): String {
            return "https://farm${farmId}.staticflickr.com/${serverId}/${id}_${secret}_${size.modifier}.jpg"
        }
    }
}
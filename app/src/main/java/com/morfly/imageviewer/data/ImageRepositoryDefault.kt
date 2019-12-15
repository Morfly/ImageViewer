package com.morfly.imageviewer.data

import com.morfly.imageviewer.domain.image.Image
import com.morfly.imageviewer.domain.image.ImageRepository

class ImageRepositoryDefault(val flickrApi: FlickrApi) : ImageRepository {

    override fun loadImages(query: String, page: Int, perPage: Int): List<Image> =
        flickrApi.searchPhotos(query, page, perPage)
            .photos.photo
            .map(Photo::toDomain)
}
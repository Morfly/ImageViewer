package com.morfly.imageviewer.domain.image

import com.morfly.imageviewer.domain.image.Image

interface ImageRepository {

    fun loadImages(query: String, page: Int, perPage: Int): List<Image>

    companion object {
        const val PER_PAGE_DEFAULT = 20
    }
}
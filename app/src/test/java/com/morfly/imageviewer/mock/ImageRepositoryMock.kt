package com.morfly.imageviewer.mock

import com.morfly.imageviewer.domain.image.Image
import com.morfly.imageviewer.domain.image.ImageRepository

class ImageRepositoryMock(val imageList: List<Image> = listOf(TestDomainModels.testImage)) : ImageRepository {

    override fun loadImages(query: String, page: Int, perPage: Int): List<Image> {
        return imageList
    }
}
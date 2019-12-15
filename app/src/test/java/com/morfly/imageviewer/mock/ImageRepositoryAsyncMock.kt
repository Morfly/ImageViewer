package com.morfly.imageviewer.mock

import com.morfly.imageviewer.domain.image.Image
import com.morfly.imageviewer.domain.image.ImageRepository
import com.morfly.imageviewer.ui.async.ImageRepositoryAsync
import com.morfly.imageviewer.ui.async.OnFailure
import com.morfly.imageviewer.ui.async.OnSuccess

class ImageRepositoryAsyncMock(val imageList: List<Image> = listOf(TestDomainModels.testImage)) : ImageRepositoryAsync {

    override fun loadImagesAsync(query: String, page: Int, perPage: Int, onSuccess: OnSuccess, onFailure: OnFailure) {
        onSuccess(imageList)
    }
}
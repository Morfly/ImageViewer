package com.morfly.imageviewer.ui.async

interface ImageRepositoryAsync {

    fun loadImagesAsync(query: String, page: Int, perPage: Int, onSuccess: OnSuccess, onFailure: OnFailure)
}
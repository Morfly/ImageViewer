package com.morfly.imageviewer.ui.async

import android.os.AsyncTask
import com.morfly.imageviewer.domain.image.ImageRepository

class ImageRepositoryAsyncDefault(private val repository: ImageRepository) : ImageRepositoryAsync {

    override fun loadImagesAsync(query: String, page: Int, perPage: Int, onSuccess: OnSuccess, onFailure: OnFailure) {
        RequestImagesTask(repository, onSuccess, onFailure)
            .executeOnExecutor(
                AsyncTask.SERIAL_EXECUTOR,
                RequestImagesTask.Params(query, page, perPage)
            )
    }
}
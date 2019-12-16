package com.morfly.imageviewer.ui.async

import android.os.AsyncTask
import com.morfly.imageviewer.domain.image.Image
import com.morfly.imageviewer.domain.image.ImageRepository
import java.lang.Exception

typealias OnSuccess = (List<Image>) -> Unit
typealias OnFailure = () -> Unit

class RequestImagesTask(
    private val repository: ImageRepository,
    private val onSuccess: OnSuccess,
    private val onFailure: OnFailure
) : AsyncTask<RequestImagesTask.Params, Unit, List<Image>>() {

    override fun doInBackground(vararg params: Params): List<Image>? {
        val (query, skip, take) = params[0]
        return try {
            repository.loadImages(query, skip, take)
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(result: List<Image>?) {
        if (result != null) onSuccess(result)
        else onFailure()
    }

    data class Params(
        val query: String,
        val page: Int,
        val perPage: Int
    )
}
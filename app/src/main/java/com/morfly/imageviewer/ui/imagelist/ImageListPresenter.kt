package com.morfly.imageviewer.ui.imagelist

import com.morfly.imageviewer.R
import com.morfly.imageviewer.domain.image.ImageRepository
import com.morfly.imageviewer.domain.image.ImageRepository.Companion.PER_PAGE_DEFAULT
import com.morfly.imageviewer.ui.async.ImageRepositoryAsync

class ImageListPresenter(private val repository: ImageRepositoryAsync) : ImageListContract.Presenter {

    override var view: ImageListContract.View? = null

    override val numberOfListColumns: Int get() = 3


    var lastQuery: String? = null
        private set


    override fun loadImages(query: String) {
        view?.showLoading()
        view?.hidePlaceholder()

        lastQuery = query
        repository.loadImagesAsync(
            query, 1, PER_PAGE_DEFAULT,
            onSuccess = {
                view?.apply {
                    displayImages(it)
                    hideLoading()
                }

            },
            onFailure = ::onImageLoadFailure
        )

    }

    override fun loadMoreImages(page: Int) {
        lastQuery?.let { query ->
            repository.loadImagesAsync(
                query, page, PER_PAGE_DEFAULT,
                onSuccess = {
                    view?.displayMoreImages(it)
                },
                onFailure = ::onImageLoadFailure
            )

        }
    }

    private fun onImageLoadFailure() {
        view?.hideLoading()
        view?.displayMessage(R.string.text_error)
    }
}
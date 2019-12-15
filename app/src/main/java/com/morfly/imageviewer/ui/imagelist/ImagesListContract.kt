package com.morfly.imageviewer.ui.imagelist

import androidx.annotation.StringRes
import com.morfly.imageviewer.domain.image.Image

interface ImagesListContract {

    interface View {

        fun displayImages(images: List<Image>)

        fun displayMoreImages(images: List<Image>)

        fun hidePlaceholder()

        fun displayMessage(@StringRes messageStringRes: Int)

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter {

        var view: View?

        val numberOfListColumns: Int

        fun loadImages(query: String)

        fun loadMoreImages(page: Int)
    }

}
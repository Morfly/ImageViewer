package com.morfly.imageviewer.mock

import com.morfly.imageviewer.domain.image.Image
import com.morfly.imageviewer.ui.imagelist.ImageListContract

class ImageListViewMock : ImageListContract.View {

    var displayImagesData: List<Image>? = null
        private set
    var displayImagesCallCount: Int = 0
        private set

    var displayMoreImagesData: List<Image>? = null
        private set
    var displayMoreImagesCallCount: Int = 0
        private set

    var isPlaceholderHidden: Boolean = false
        private set

    var lastDisplayedMessageRes: Int? = null
        private set

    var isLoadingDisplayed: Boolean = false
        private set
    var showLoadingCallCount: Int = 0
        private set
    var hideLoadingCallCount: Int = 0
        private set


    override fun displayImages(images: List<Image>) {
        displayImagesCallCount++
        displayImagesData = images
    }

    override fun displayMoreImages(images: List<Image>) {
        displayMoreImagesCallCount++
        displayMoreImagesData = images
    }

    override fun hidePlaceholder() {
        isPlaceholderHidden = true
    }

    override fun displayMessage(messageStringRes: Int) {
        lastDisplayedMessageRes = messageStringRes
    }

    override fun showLoading() {
        showLoadingCallCount++
        isLoadingDisplayed = true
    }

    override fun hideLoading() {
        isLoadingDisplayed = false
        hideLoadingCallCount++
    }
}
package com.morfly.imageviewer

import com.morfly.imageviewer.domain.image.ImageRepository
import com.morfly.imageviewer.mock.ImageListViewMock
import com.morfly.imageviewer.mock.ImageRepositoryAsyncMock
import com.morfly.imageviewer.mock.ImageRepositoryMock
import com.morfly.imageviewer.mock.TestDomainModels
import com.morfly.imageviewer.ui.async.ImageRepositoryAsync
import com.morfly.imageviewer.ui.async.ImageRepositoryAsyncDefault
import com.morfly.imageviewer.ui.imagelist.ImageListPresenter
import org.junit.Assert.*
import org.junit.Test

class ImageListPresenterTest {

    @Test
    fun `Test state after initialization`() {

        // Given
        val imageRepository: ImageRepositoryAsync = ImageRepositoryAsyncMock()
        val givenPresenter = ImageListPresenter(imageRepository)
        val givenView = ImageListViewMock()
        givenPresenter.view = givenView
        val givenSearchQuery = "query"

        // When
        // Idle

        // Then
        with(givenView) {
            assertEquals(displayImagesData, null)
            assertEquals(displayImagesCallCount, 0)
            assertEquals(displayMoreImagesData, null)
            assertEquals(displayMoreImagesCallCount, 0)
            assertFalse(isPlaceholderHidden)
            assertFalse(isLoadingDisplayed)
            assertEquals(showLoadingCallCount, 0)
            assertEquals(hideLoadingCallCount, 0)
        }
        assertEquals(null, givenPresenter.lastQuery)
    }

    @Test
    fun `loadImages test`() {
        // Given
        val imageRepository: ImageRepositoryAsync = ImageRepositoryAsyncMock()
        val givenPresenter = ImageListPresenter(imageRepository)
        val givenView = ImageListViewMock()
        givenPresenter.view = givenView
        val givenSearchQuery = "query"

        // When
        givenPresenter.loadImages(givenSearchQuery)

        // Then

        with(givenView) {
            assertEquals(displayImagesData, listOf(TestDomainModels.testImage))
            assertEquals(displayImagesCallCount, 1)
            assertEquals(displayMoreImagesData, null)
            assertEquals(displayMoreImagesCallCount, 0)
            assertTrue(isPlaceholderHidden)
            assertFalse(isLoadingDisplayed)
            assertEquals(showLoadingCallCount, 1)
            assertEquals(hideLoadingCallCount, 1)
        }
        assertEquals(givenSearchQuery, givenPresenter.lastQuery)
    }

    @Test
    fun `loadMoreImages without prior loadImages`() {
        // Given
        val imageRepository: ImageRepositoryAsync = ImageRepositoryAsyncMock()
        val givenPresenter = ImageListPresenter(imageRepository)
        val givenView = ImageListViewMock()
        givenPresenter.view = givenView

        // When
        givenPresenter.loadMoreImages(2)

        // Then
        with(givenView) {
            assertEquals(displayImagesData, null)
            assertEquals(displayImagesCallCount, 0)
            assertEquals(displayMoreImagesData, null)
            assertEquals(displayMoreImagesCallCount, 0)
            assertFalse(isPlaceholderHidden)
            assertFalse(isLoadingDisplayed)
            assertEquals(showLoadingCallCount, 0)
            assertEquals(hideLoadingCallCount, 0)
        }
        assertEquals(null, givenPresenter.lastQuery)

    }

    @Test
    fun `loadMoreImages after loadImages`() {
        // Given
        val imageRepository: ImageRepositoryAsync = ImageRepositoryAsyncMock()
        val givenPresenter = ImageListPresenter(imageRepository)
        val givenView = ImageListViewMock()
        givenPresenter.view = givenView
        val givenSearchQuery = "query"

        // When
        givenPresenter.loadImages(givenSearchQuery)
        givenPresenter.loadMoreImages(2)

        // Then
        with(givenView) {
            assertEquals(displayImagesData, listOf(TestDomainModels.testImage))
            assertEquals(displayImagesCallCount, 1)
            assertEquals(displayMoreImagesData, listOf(TestDomainModels.testImage))
            assertEquals(displayMoreImagesCallCount, 1)
            assertTrue(isPlaceholderHidden)
            assertFalse(isLoadingDisplayed)
            assertEquals(showLoadingCallCount, 1)
            assertEquals(hideLoadingCallCount, 1)
        }
        assertEquals(givenSearchQuery, givenPresenter.lastQuery)

    }

    @Test
    fun `loadMoreImages without view does not crash`() {

        // Given
        val imageRepository: ImageRepositoryAsync = ImageRepositoryAsyncMock()
        val givenPresenter = ImageListPresenter(imageRepository)
        val givenView = ImageListViewMock()

        // When
        givenPresenter.loadImages("query")

        // Then
        // No crash
    }
}
package com.morfly.imageviewer

import com.morfly.imageviewer.data.ImageRepositoryDefault
import com.morfly.imageviewer.domain.image.ImageRepository
import com.morfly.imageviewer.mock.FlickrApiMock
import com.morfly.imageviewer.mock.TestDataModels
import com.morfly.imageviewer.mock.TestDomainModels
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageRepositoryDefaultTest {

    @Test
    fun `loadImages returns expected list of images`() {

        // Given
        val flickrApi = FlickrApiMock()
        val givenRepository: ImageRepository = ImageRepositoryDefault(flickrApi)

        // When
        val images = givenRepository.loadImages("", 0, 0)

        // Then
        assertEquals(images.size, 1)
        assertEquals(images[0], TestDomainModels.testImage)
    }

    @Test
    fun `loadImages returns empty list of images if response did not coitain photos`() {

        // Given
        val flickrApi = FlickrApiMock(photoSearchResponse = TestDataModels.testEmptyPhotoSearchResponse)
        val givenRepository: ImageRepository = ImageRepositoryDefault(flickrApi)

        // When
        val images = givenRepository.loadImages("", 0, 0)

        // Then
        assertEquals(images.size, 0)
    }

}
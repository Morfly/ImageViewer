package com.morfly.imageviewer

import com.morfly.imageviewer.data.FlickrApi
import com.morfly.imageviewer.data.FlickrApiDefault
import org.junit.Test
import org.junit.Assert.*


class FlickrApiTest {

    @Test
    fun `buildImageUrl generator test`() {
        // Given
        val givenFarmId = "1"
        val givenServerId = "22"
        val givenId = "333"
        val givenSecret = "4444"

        val expectedUrl = "https://farm1.staticflickr.com/22/333_4444.jpg"

        // When
        val url = FlickrApi.buildImageUrl(
            farmId = givenFarmId,
            serverId = givenServerId,
            id = givenId,
            secret = givenSecret
        )

        // Then
        assertEquals(url, expectedUrl)
    }

    @Test
    fun `photoSearchUrl generator test`() {
        // Given
        val givenTags = "kitten"
        val givenPage = 1
        val givenPerPage = 100

        val givenFlickrApi = FlickrApiDefault()

        val expectedUrl = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=7f7b3aca5d7c9cb076142a32d0ae67de&tags=kitten&per_page=100&page=1&format=json&nojsoncallback=1"

        // When
        val photoSearchUrl = givenFlickrApi.photoSearchUrl(
            tags = givenTags,
            page = givenPage,
            perPage = givenPerPage
        )

        // Then
        assertEquals(photoSearchUrl, expectedUrl)
    }
}
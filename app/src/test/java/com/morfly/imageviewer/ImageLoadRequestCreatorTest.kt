package com.morfly.imageviewer

import com.morfly.imageviewer.imageloader.*
import com.morfly.imageviewer.mock.ImageMemoryCacheManagerStub
import org.junit.Assert.assertTrue
import org.junit.Test

class ImageLoadRequestCreatorTest {

    @Test
    fun `ImageLoadRequestCreator contains no policies applied if non of them were set`() {

        // Given
        val imageLoader = ImageLoader(ImageMemoryCacheManagerStub())
        val givenRequestCreator = ImageLoadRequestCreator(imageLoader, "")

        // When
        // No loading policy set

        // Then
        assertTrue(ImageLoadingPolicy.OFFLINE.isNotAppliedTo(givenRequestCreator.loadingPolicy))
        assertTrue(ImageLoadingPolicy.NO_MEMORY_CACHE.isNotAppliedTo(givenRequestCreator.loadingPolicy))
    }

    @Test
    fun `ImageLoadRequestCreator contains main policy which was set`() {

        // Given
        val imageLoader = ImageLoader(ImageMemoryCacheManagerStub())
        val givenRequestCreator = ImageLoadRequestCreator(imageLoader, "")

        // When
        givenRequestCreator.loadingPolicy(ImageLoadingPolicy.NO_MEMORY_CACHE)

        // Then
        assertTrue(ImageLoadingPolicy.OFFLINE.isNotAppliedTo(givenRequestCreator.loadingPolicy))
        assertTrue(ImageLoadingPolicy.NO_MEMORY_CACHE.isAppliedTo(givenRequestCreator.loadingPolicy))
    }

    @Test
    fun `ImageLoadRequestCreator contains multiple policies which were set`() {

        // Given
        val imageLoader = ImageLoader(ImageMemoryCacheManagerStub())
        val givenRequestCreator = ImageLoadRequestCreator(imageLoader, "")

        // When
        givenRequestCreator.loadingPolicy(ImageLoadingPolicy.NO_MEMORY_CACHE, ImageLoadingPolicy.OFFLINE)

        // Then
        assertTrue(ImageLoadingPolicy.OFFLINE.isAppliedTo(givenRequestCreator.loadingPolicy))
        assertTrue(ImageLoadingPolicy.NO_MEMORY_CACHE.isAppliedTo(givenRequestCreator.loadingPolicy))
    }
}
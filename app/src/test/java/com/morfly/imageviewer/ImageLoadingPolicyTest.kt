package com.morfly.imageviewer

import com.morfly.imageviewer.imageloader.ImageLoadingPolicy
import com.morfly.imageviewer.imageloader.isAppliedTo
import com.morfly.imageviewer.imageloader.isNotAppliedTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ImageLoadingPolicyTest {

    @Test
    fun `isAppliedTo returns true if flag set`() {
        // Given
        val givenPolicy = ImageLoadingPolicy.NO_MEMORY_CACHE()

        // When
        val isAppliedTo = ImageLoadingPolicy.NO_MEMORY_CACHE isAppliedTo givenPolicy

        // Then
        assertTrue(isAppliedTo)
    }

    @Test
    fun `isAppliedTo returns false if flag is not set`() {
        // Given
        val givenPolicy = ImageLoadingPolicy.NO_MEMORY_CACHE()

        // When
        val isAppliedTo = ImageLoadingPolicy.OFFLINE isAppliedTo givenPolicy

        // Then
        assertFalse(isAppliedTo)
    }

    @Test
    fun `isAppliedTo returns true if multiple flags set`() {
        // Given
        val givenPolicy = ImageLoadingPolicy.NO_MEMORY_CACHE() or ImageLoadingPolicy.OFFLINE()

        // When
        val isAppliedTo = ImageLoadingPolicy.OFFLINE isAppliedTo givenPolicy

        // Then
        assertTrue(isAppliedTo)
    }

    @Test
    fun `isAppliedTo returns false if no flags set`() {
        // Given
        val givenPolicy = 0

        // When
        val isAppliedTo = ImageLoadingPolicy.OFFLINE isAppliedTo givenPolicy

        // Then
        assertFalse(isAppliedTo)
    }


    @Test
    fun `isNotAppliedTo returns false if flag set`() {
        // Given
        val givenPolicy = ImageLoadingPolicy.NO_MEMORY_CACHE()

        // When
        val isAppliedTo = ImageLoadingPolicy.NO_MEMORY_CACHE isNotAppliedTo givenPolicy

        // Then
        assertFalse(isAppliedTo)
    }

    @Test
    fun `isNotAppliedTo returns true if flag is not set`() {
        // Given
        val givenPolicy = ImageLoadingPolicy.NO_MEMORY_CACHE()

        // When
        val isAppliedTo = ImageLoadingPolicy.OFFLINE isNotAppliedTo givenPolicy

        // Then
        assertTrue(isAppliedTo)
    }

    @Test
    fun `isNotAppliedTo returns false if multiple flags set`() {
        // Given
        val givenPolicy = ImageLoadingPolicy.NO_MEMORY_CACHE() or ImageLoadingPolicy.OFFLINE()

        // When
        val isAppliedTo = ImageLoadingPolicy.OFFLINE isNotAppliedTo givenPolicy

        // Then
        assertFalse(isAppliedTo)
    }

    @Test
    fun `isNotAppliedTo returns true if no flags set`() {
        // Given
        val givenPolicy = 0

        // When
        val isAppliedTo = ImageLoadingPolicy.OFFLINE isNotAppliedTo givenPolicy

        // Then
        assertTrue(isAppliedTo)
    }
}

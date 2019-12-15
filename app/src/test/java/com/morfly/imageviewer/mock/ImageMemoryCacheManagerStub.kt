package com.morfly.imageviewer.mock

import android.graphics.Bitmap
import com.morfly.imageviewer.imageloader.ImageMemoryCacheManager

class ImageMemoryCacheManagerStub : ImageMemoryCacheManager {

    override fun saveToMemoryCache(key: String, bitmap: Bitmap) {
        // Stub
    }

    override fun getFromMemoryCache(key: String): Bitmap? {
        return null
    }
}
package com.morfly.imageviewer.imageloader

import android.graphics.Bitmap


interface ImageCacheManager {

    fun saveToMemoryCache(bitmap: Bitmap)

    fun saveToDiskCache(bitmap: Bitmap)

    fun getFromMemoryCache(): Bitmap?

    fun getFromDiskCache(): Bitmap?
}


class ImageCacheManagerDefault: ImageCacheManager {

    override fun saveToMemoryCache(bitmap: Bitmap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveToDiskCache(bitmap: Bitmap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFromMemoryCache(): Bitmap? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFromDiskCache(): Bitmap? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
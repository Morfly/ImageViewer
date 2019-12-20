package com.morfly.imageviewer.imageloader

import android.graphics.Bitmap
import android.os.AsyncTask
import android.graphics.BitmapFactory
import com.morfly.imageviewer.lib.weak
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL


internal class DownloadImageTask(
    imageLoader: ImageLoader,
    private val loadingPolicy: Int,
    private val listener: ImageLoadListener
) : AsyncTask<DownloadImageTask.Arg, Unit, Bitmap>() {

    private val imageLoader by weak(imageLoader)

    private val canUseCache: Boolean = ImageLoadingPolicy.NO_MEMORY_CACHE isNotAppliedTo loadingPolicy
    private val canUseNetwork: Boolean = ImageLoadingPolicy.OFFLINE isNotAppliedTo loadingPolicy

    override fun doInBackground(vararg args: Arg): Bitmap? {
        val (url: String, width: Int, height: Int) = args[0]

        var bitmap: Bitmap? = null

        try {
            fun openInputStream() = URL(url).openStream()

            if (canUseCache) {
                bitmap = findInCache(key = url)
            }

            if (canUseNetwork && bitmap == null) {
                bitmap = loadFromInternet(url, width, height)

                if (canUseCache && bitmap != null) {
                    saveToCache(key = url, bitmap = bitmap)
                }
            }

        } catch (malformedUrlException: MalformedURLException) {
            // Handle error
        } catch (ioException: IOException) {
            // Handle error
        }

        return bitmap
    }

    override fun onPostExecute(loadedBitmap: Bitmap?) {
        if (loadedBitmap != null) listener.onSuccess(loadedBitmap)
        else listener.onFailure()
    }

    private fun findInCache(key: String): Bitmap? {
        return imageLoader?.cacheManager?.getFromMemoryCache(key)
    }

    private fun saveToCache(key: String, bitmap: Bitmap) {
        imageLoader?.cacheManager?.saveToMemoryCache(key, bitmap)
    }

    private fun loadFromInternet(url: String, width: Int, height: Int): Bitmap? {
        fun openInputStream() = URL(url).openStream()

        var inputStream = openInputStream()

        val options = BitmapFactory.Options()

        // Adjust bitmap options as per ImageView size. This will allow to optimize image loading from the internet
        if (width > 0 && height > 0) {
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)
            options.inSampleSize =
                calculateBitmapInSampleSize(options, requiredWidth = width, requiredHeight = height)
            options.inJustDecodeBounds = false

            inputStream = openInputStream()
        }

        return BitmapFactory.decodeStream(inputStream, null, options)
    }

    data class Arg(
        val url: String,
        val requiredWidth: Int,
        val requiredHeight: Int
    )

}

private fun calculateBitmapInSampleSize(options: BitmapFactory.Options, requiredWidth: Int, requiredHeight: Int): Int {

    val (bitmapHeight: Int, bitmapWidth: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (bitmapHeight > requiredHeight || bitmapWidth > requiredWidth) {
        val halfHeight: Int = bitmapHeight / 2
        val halfWidth: Int = bitmapWidth / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= requiredHeight && halfWidth / inSampleSize >= requiredWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}


interface ImageLoadListener {
    fun onSuccess(bitmap: Bitmap)
    fun onFailure()
}


package com.morfly.imageviewer.imageloader

import android.graphics.Bitmap
import android.os.AsyncTask
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.morfly.imageviewer.weak
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*


internal class DownloadImageTask(
    private val loadingPolicy: Int,
    private val listener: ImageLoadListener
) : AsyncTask<DownloadImageTask.Arg, Unit, Bitmap>() {

    override fun doInBackground(vararg args: Arg): Bitmap? {
        val (url: String, width: Int, height: Int) = args[0]

        var bitmap: Bitmap? = null

        try {
            fun openInputStream() = URL(url).openStream()

            bitmap = BitmapFactory.Options().let {
                var inputStream = openInputStream()

                if (width > 0 && height > 0) {

                    it.inJustDecodeBounds = true
                    BitmapFactory.decodeStream(inputStream, null, it)
                    it.inSampleSize = calculateBitmapInSampleSize(it, requiredWidth = width, requiredHeight = height)
                    it.inJustDecodeBounds = false

                    inputStream = openInputStream()
                }

                BitmapFactory.decodeStream(inputStream, null, it)
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

    data class Arg(
        val url: String,
        val requiredWidth: Int,
        val requiredHeight: Int
    )

}

fun calculateBitmapInSampleSize(options: BitmapFactory.Options, requiredWidth: Int, requiredHeight: Int): Int {

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


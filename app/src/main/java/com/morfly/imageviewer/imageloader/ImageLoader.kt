package com.morfly.imageviewer.imageloader

import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.morfly.imageviewer.weak
import java.util.*


class ImageLoader(private val cacheManager: ImageCacheManager) {

    private val activeLoadRequests: MutableMap<Any, ImageLoadRequest> = WeakHashMap()

    fun load(url: String?): ImageLoadRequestCreator {
        return ImageLoadRequestCreator(this, url!!)
    }

    fun cancelRequest(imageView: ImageView) {
        imageView.apply {
            if (drawable != null) setImageBitmap(null)
        }
        activeLoadRequests.remove(imageView)
    }

    internal fun registerLoadRequest(target: Any, loadRequest: ImageLoadRequest) {
        if (target is ImageView) cancelRequest(target)

        Log.i(LOG_TAG, "registerLoadRequest")
        activeLoadRequests[target] = loadRequest
        loadRequest.execute(object : ImageLoadListener {
            override fun onSuccess(bitmap: Bitmap) {
                if (activeLoadRequests[target] == loadRequest) {
                    Log.i(LOG_TAG, "Image loaded")
                    (target as ImageView).setImageBitmap(bitmap)
                    activeLoadRequests.remove(target)
                    Log.i(LOG_TAG, "loadRequests: ${activeLoadRequests.size}")
                }
            }

            override fun onFailure() {

            }

        })

    }

    companion object {
        private val LOG_TAG = ImageLoader::class.java.simpleName
    }

}

class ImageLoadRequestCreator(imageLoader: ImageLoader, private val url: String) {
    private val imageLoader by weak(imageLoader)

    private var loadingPolicy: Int = 0

    fun loadingPolicy(loadingPolicy: LoadingPolicy, vararg additionalPolicies: LoadingPolicy) {
        this.loadingPolicy = loadingPolicy()
    }

    fun into(imageView: ImageView) {
        val size = imageView.run { maxWidth to maxHeight }
        imageLoader?.registerLoadRequest(
            imageView,
            ImageLoadRequest(loadingPolicy, url, size)
        )
    }
}

class ImageLoadRequest(
    private val loadingPolicy: Int,
    private val url: String,
    private val size: Pair<Int, Int> = 0 to 0
) {

    fun execute(listener: ImageLoadListener) {
        DownloadImageTask(loadingPolicy, listener).executeOnExecutor(
            AsyncTask.THREAD_POOL_EXECUTOR,
            DownloadImageTask.Arg(url, requiredWidth = size.first, requiredHeight = size.second)
        )
    }

}
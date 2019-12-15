package com.morfly.imageviewer.imageloader

import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.util.*


class ImageLoader(val cacheManager: ImageMemoryCacheManager) {

    private val activeLoadRequests: MutableMap<Any, ImageLoadRequest> = WeakHashMap()

    fun load(url: String): ImageLoadRequestCreator {
        return ImageLoadRequestCreator(this, url)
    }

    fun cancelRequest(imageView: ImageView) {
        imageView.apply {
            if (drawable != null) setImageBitmap(null)
        }
        activeLoadRequests.remove(imageView)
    }

    internal fun registerLoadRequest(target: Any, loadRequest: ImageLoadRequest) {
        if (target is ImageView) cancelRequest(target)

        activeLoadRequests[target] = loadRequest
        loadRequest.execute(object : ImageLoadListener {
            override fun onSuccess(bitmap: Bitmap) {
                if (activeLoadRequests[target] == loadRequest) {
                    (target as ImageView).setImageBitmap(bitmap)
                    activeLoadRequests.remove(target)
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

class ImageLoadRequestCreator(private val imageLoader: ImageLoader, private val url: String) {

    var loadingPolicy: Int = 0
        private set

    fun loadingPolicy(
        loadingPolicy: ImageLoadingPolicy,
        vararg additionalPolicies: ImageLoadingPolicy
    ): ImageLoadRequestCreator {
        this.loadingPolicy = loadingPolicy()
        additionalPolicies.forEach {
            this.loadingPolicy = this.loadingPolicy or it()
        }
        return this
    }

    fun into(imageView: ImageView) {
        val size = imageView.run { maxWidth to maxHeight }
        imageLoader.registerLoadRequest(
            imageView,
            ImageLoadRequest(imageLoader, loadingPolicy, url, size)
        )
    }
}

class ImageLoadRequest(
    private val imageLoader: ImageLoader,
    private val loadingPolicy: Int,
    private val url: String,
    private val size: Pair<Int, Int> = 0 to 0
) {

    fun execute(listener: ImageLoadListener) {
        DownloadImageTask(imageLoader, loadingPolicy, listener).executeOnExecutor(
            AsyncTask.THREAD_POOL_EXECUTOR,
            DownloadImageTask.Arg(url, requiredWidth = size.first, requiredHeight = size.second)
        )
    }

}
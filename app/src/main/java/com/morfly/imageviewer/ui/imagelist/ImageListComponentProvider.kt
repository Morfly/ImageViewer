package com.morfly.imageviewer.ui.imagelist

import com.morfly.imageviewer.data.FlickrApi
import com.morfly.imageviewer.data.FlickrApiDefault
import com.morfly.imageviewer.data.ImageRepositoryDefault
import com.morfly.imageviewer.domain.image.ImageRepository
import com.morfly.imageviewer.imageloader.ImageCacheManager
import com.morfly.imageviewer.imageloader.ImageCacheManagerDefault
import com.morfly.imageviewer.imageloader.ImageLoader

class ImageListComponentProvider {

    // Data
    private val flickrApi: FlickrApi by lazy { FlickrApiDefault() }
    private val imageCacheManager: ImageCacheManager by lazy { ImageCacheManagerDefault() }
    private val imageLoader: ImageLoader by lazy { ImageLoader(imageCacheManager) }
    private val imageRepository: ImageRepository by lazy { ImageRepositoryDefault(flickrApi) }

    // UI
    val presenter: ImagesListContract.Presenter = ImagesListPresenter(imageRepository)
    val imageListAdapter: ImageListAdapter = ImageListAdapter(imageLoader)
}
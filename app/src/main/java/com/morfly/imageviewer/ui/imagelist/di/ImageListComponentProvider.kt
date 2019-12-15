package com.morfly.imageviewer.ui.imagelist.di

import com.morfly.imageviewer.data.FlickrApi
import com.morfly.imageviewer.data.FlickrApiDefault
import com.morfly.imageviewer.data.ImageRepositoryDefault
import com.morfly.imageviewer.domain.image.ImageRepository
import com.morfly.imageviewer.imageloader.ImageLoader
import com.morfly.imageviewer.imageloader.ImageMemoryCacheManager
import com.morfly.imageviewer.imageloader.ImageMemoryCacheManagerDefault
import com.morfly.imageviewer.ui.async.ImageRepositoryAsync
import com.morfly.imageviewer.ui.async.ImageRepositoryAsyncDefault
import com.morfly.imageviewer.ui.imagelist.ImageListAdapter
import com.morfly.imageviewer.ui.imagelist.ImageListContract
import com.morfly.imageviewer.ui.imagelist.ImageListPresenter

class ImageListComponentProvider {

    // Data
    private val flickrApi: FlickrApi by lazy { FlickrApiDefault() }
    private val imageCacheManager: ImageMemoryCacheManager by lazy { ImageMemoryCacheManagerDefault() }
    private val imageLoader: ImageLoader by lazy { ImageLoader(imageCacheManager) }
    private val imageRepository: ImageRepository by lazy { ImageRepositoryDefault(flickrApi) }

    // UI
    private val imageRepositoryAsync: ImageRepositoryAsync by lazy { ImageRepositoryAsyncDefault(imageRepository) }
    val presenter: ImageListContract.Presenter =
        ImageListPresenter(imageRepositoryAsync)
    val imageListAdapter: ImageListAdapter =
        ImageListAdapter(imageLoader)
}
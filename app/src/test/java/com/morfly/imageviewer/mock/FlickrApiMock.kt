package com.morfly.imageviewer.mock

import com.morfly.imageviewer.data.FlickrApi
import com.morfly.imageviewer.data.PhotoSearchResponse

class FlickrApiMock(
    val photoSearchResponse: PhotoSearchResponse = TestDataModels.testPhotoSearchResponse
) : FlickrApi {

    override fun searchPhotos(query: String, page: Int, perPage: Int): PhotoSearchResponse {
        return photoSearchResponse
    }

}
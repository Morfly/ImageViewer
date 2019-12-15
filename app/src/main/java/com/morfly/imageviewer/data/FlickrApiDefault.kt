package com.morfly.imageviewer.data

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FlickrApiDefault : FlickrApi {

    override fun searchPhotos(query: String, page: Int, perPage: Int): PhotoSearchResponse {
        var urlConnection: HttpURLConnection? = null
        return try {
            val url = URL(photoSearchUrl(query, page, perPage))
            Log.i(LOG_TAG, "Requesting Flickr photos by url: $url")
            urlConnection = url.openConnection() as HttpURLConnection

            val code = urlConnection.responseCode
            if (code != 200) {
                throw IOException("Invalid response from server: $code");
            }

            val stringBuilder = StringBuilder()
            BufferedReader(InputStreamReader(urlConnection.inputStream))
                .forEachLine {
                    stringBuilder.append("$it\n")
                }

            JSONObject(stringBuilder.toString()).toPhotoSearchResponse()
        } finally {
            urlConnection?.disconnect()
        }
    }

    fun photoSearchUrl(tags: String, page: Int, perPage: Int): String {
        return "$API_ENDPOINT$Q_PHOTO_SEARCH$Q_API_KEY$API_KEY$Q_TAG$tags$Q_PER_PAGE$perPage$Q_PAGE$page$Q_FORMAT$Q_NO_JSON_CALLBACK"
    }

    companion object {

        private val LOG_TAG = FlickrApiDefault::class.java.simpleName

        private const val API_ENDPOINT = "https://www.flickr.com/services/rest/"
        private const val API_KEY = "7f7b3aca5d7c9cb076142a32d0ae67de"

        private const val Q_PHOTO_SEARCH = "?method=flickr.photos.search"
        private const val Q_TAG = "&tags="
        private const val Q_API_KEY = "&api_key="

        private const val Q_PAGE = "&page="
        private const val Q_PER_PAGE = "&per_page="

        private const val Q_NO_JSON_CALLBACK = "&nojsoncallback=1"
        private const val Q_FORMAT = "&format=json"
    }

}
package com.morfly.imageviewer.data

import com.morfly.imageviewer.domain.image.Image
import org.json.JSONArray
import org.json.JSONObject
import java.lang.IllegalArgumentException


fun JSONObject.toPhotoSearchResponse() = PhotoSearchResponse(
    stat = getString("stat"),
    photos = getJSONObject("photos").toPhotos()
)

fun JSONObject.toPhotos() = Photos(
    page = getInt("page"),
    perPage = getInt("perpage"),
    pages = getInt("pages"),
    photo = getJSONArray("photo").asList().map { it.toPhoto() },
    total = getString("total")
)

fun JSONObject.toPhoto() = Photo(
    farm = getInt("farm"),
    id = getString("id"),
    server = getString("server"),
    secret = getString("secret"),
    isFamily = getInt("isfamily").toBoolean(),
    isFriend = getInt("isfriend").toBoolean(),
    isPublic = getInt("ispublic").toBoolean(),
    owner = getString("owner"),
    title = getString("title")
)


fun Photo.toDomain(): Image = Image(
    id = id,
    url = FlickrApi.buildImageUrl(
        farmId = farm.toString(),
        serverId = server,
        id = id,
        secret = secret
    ),
    thumbUrl = FlickrApi.buildImageUrl(
        farmId = farm.toString(),
        serverId = server,
        id = id,
        secret = secret,
        size = FlickrApi.ImageSize.THUMB
    )
)


fun Int.toBoolean(): Boolean {
    return when (this) {
        1 -> true
        0 -> false
        else -> throw IllegalArgumentException("Unable to convert Int to Boolean. Int must be 0 or 1")
    }
}

fun JSONArray.asList(): List<JSONObject> {
    val list = mutableListOf<JSONObject>()
    for (i in 0 until length()) {
        list.add(this.getJSONObject(i))
    }
    return list
}
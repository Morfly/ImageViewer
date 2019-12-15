package com.morfly.imageviewer

import com.morfly.imageviewer.data.*
import com.morfly.imageviewer.domain.image.Image
import org.json.JSONException
import org.junit.Assert.*
import org.json.JSONObject
import org.junit.Test

class DataUtilsTest {

    @Test
    fun `jsonToPhoto with correct json`() {
        // Given
        val givenJsonString = """
            { 
                "id":"49221646631",
                "owner":"186005158@N05",
                "secret":"5236647c43",
                "server":"65535",
                "farm":66,
                "title":"Kitten eyes",
                "ispublic":1,
                "isfriend":0,
                "isfamily":0
            }
        """.trimIndent()
        val givenJsonObject = JSONObject(givenJsonString)

        // When
        val photo = givenJsonObject.toPhoto()

        // Then
        with(photo) {
            assertEquals(id, "49221646631")
            assertEquals(owner, "186005158@N05")
            assertEquals(secret, "5236647c43")
            assertEquals(server, "65535")
            assertEquals(farm, 66)
            assertEquals(title, "Kitten eyes")
            assertTrue(isPublic)
            assertFalse(isFriend)
            assertFalse(isFamily)
        }
    }

    @Test(expected = JSONException::class)
    fun `jsonToPhoto with invalid json`() {
        // Given
        val givenJsonString = "{invalidJson}"
        val givenJsonObject = JSONObject(givenJsonString)

        // When
        val photo = givenJsonObject.toPhoto()

        // Then {Exception}
    }

    @Test
    fun `jsonToPhoto with empty string fields`() {
        // Given
        val givenJsonString = """
            { 
                "id":"",
                "owner":"",
                "secret":"",
                "server":"",
                "farm":0,
                "title":"",
                "ispublic":0,
                "isfriend":0,
                "isfamily":0
            }
        """.trimIndent()

        val givenJsonObject = JSONObject(givenJsonString)

        // When
        val photo = givenJsonObject.toPhoto()

        // Then
        with(photo) {
            assertEquals(id, "")
            assertEquals(owner, "")
            assertEquals(secret, "")
            assertEquals(server, "")
            assertEquals(title, "")
        }
    }

    @Test
    fun `jsonToPhotos with correct json`() {
        // Given
        val givenJsonString = """
            { 
                "page":1,
                "pages":502815,
                "perpage":2,
                "total":"1005630",
                "photo":[
                    { 
                       "id":"49221164563",
                        "owner":"186005158@N05",
                        "secret":"33bb115ce1",
                        "server":"65535",
                        "farm":66,
                        "title":"Sleepy kitten",
                        "ispublic":1,
                        "isfriend":0,
                        "isfamily":0
                    }
                ]
            }
        """.trimIndent()

        val givenJsonObject = JSONObject(givenJsonString)

        val givenPhoto = Photo(
            id = "49221164563",
            owner = "186005158@N05",
            secret = "33bb115ce1",
            server = "65535",
            farm = 66,
            title = "Sleepy kitten",
            isPublic = true,
            isFriend = false,
            isFamily = false
        )

        // When
        val photos = givenJsonObject.toPhotos()

        with(photos) {
            assertEquals(page, 1)
            assertEquals(pages, 502815)
            assertEquals(perPage, 2)
            assertEquals(total, "1005630")

            assertEquals(this.photo[0], givenPhoto)
        }
    }

    @Test
    fun `jsonToPhotoSearchResponse with correct json`() {
        // Given
        val givenJsonString = """
            { 
               "photos":{ 
                  "page":1,
                  "pages":502815,
                  "perpage":2,
                  "total":"1005630",
                  "photo":[ 
                     { 
                        "id":"49221164563",
                        "owner":"186005158@N05",
                        "secret":"33bb115ce1",
                        "server":"65535",
                        "farm":66,
                        "title":"Sleepy kitten",
                        "ispublic":1,
                        "isfriend":0,
                        "isfamily":0
                     }
                  ]
               },
               "stat":"ok"
            }

        """.trimIndent()

        val givenJsonObject = JSONObject(givenJsonString)

        val givenPhoto = Photo(
            id = "49221164563",
            owner = "186005158@N05",
            secret = "33bb115ce1",
            server = "65535",
            farm = 66,
            title = "Sleepy kitten",
            isPublic = true,
            isFriend = false,
            isFamily = false
        )

        val givenPhotos = Photos(
            page = 1,
            pages = 502815,
            perPage = 2,
            photo = listOf(givenPhoto),
            total = "1005630"
        )

        // When
        val photoSearchResponse = givenJsonObject.toPhotoSearchResponse()

        // Then
        with(photoSearchResponse) {
            assertEquals(stat, "ok")
            assertEquals(photos, givenPhotos)
        }
    }

    @Test
    fun `toDomain maps Photo to Image`() {

        // Given
        val givenPhoto = Photo(
            id = "49221164563",
            owner = "186005158@N05",
            secret = "33bb115ce1",
            server = "65535",
            farm = 66,
            title = "Sleepy kitten",
            isPublic = true,
            isFriend = false,
            isFamily = false
        )

        val expectedImage = Image(
            id = "49221164563",
            url = "https://farm66.staticflickr.com/65535/49221164563_33bb115ce1_b.jpg",
            thumbUrl = "https://farm66.staticflickr.com/65535/49221164563_33bb115ce1_m.jpg"
        )

        // When
        val image = givenPhoto.toDomain()

        // Then
        assertEquals(image, expectedImage)
    }


    @Test
    fun `intToBoolean with 1 to true conversion`() {
        val givenInt = 1
        assertTrue(givenInt.toBoolean())
    }

    @Test
    fun `intToBoolean with 0 to false conversion`() {
        val givenInt = 0
        assertFalse(givenInt.toBoolean())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `intToBoolean with invalid argument and expected thrown exception`() {
        val givenInt = 234
        givenInt.toBoolean()
    }
}
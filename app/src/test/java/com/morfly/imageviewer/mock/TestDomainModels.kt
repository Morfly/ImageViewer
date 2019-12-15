package com.morfly.imageviewer.mock

import com.morfly.imageviewer.domain.image.Image


object TestDomainModels {

    val testImage: Image
        get() = Image(
            id = "49221164563",
            url = "https://farm66.staticflickr.com/65535/49221164563_33bb115ce1_b.jpg",
            thumbUrl = "https://farm66.staticflickr.com/65535/49221164563_33bb115ce1_m.jpg"
        )
}
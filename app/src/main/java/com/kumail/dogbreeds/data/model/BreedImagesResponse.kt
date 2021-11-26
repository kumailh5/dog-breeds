package com.kumail.dogbreeds.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kumailhussain on 12/10/2021.
 */
@JsonClass(generateAdapter = true)
data class BreedImagesResponse(
    @Json(name = "message")
    val breedImageUrls: List<String>,
    @Json(name = "status")
    val status: String
)
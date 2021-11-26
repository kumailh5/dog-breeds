package com.kumail.dogbreeds.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kumailhussain on 12/10/2021.
 */
@JsonClass(generateAdapter = true)
data class BreedsListResponse(
    @Json(name = "message")
    val breedsList: Map<String, List<String>>,
    @Json(name = "status")
    val status: String
)
package com.kumail.dogbreeds.data.model

import com.kumail.dogbreeds.R
import com.kumail.dogbreeds.util.Strings
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kumailhussain on 14/10/2021.
 */
@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "message")
    val errorMessage: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "code")
    val code: Int
) {
    constructor() : this(
        Strings.get(R.string.error_something_went_wrong),
        Strings.get(R.string.error), 0
    )
}
package com.kumail.dogbreeds.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kumailhussain on 14/10/2021.
 */
data class ErrorResponse(
    @SerializedName("message")
    val errorMessage: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("code")
    val codes: Int
)
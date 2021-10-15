package com.kumail.dogbreeds.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kumailhussain on 12/10/2021.
 */
data class BreedImagesResponse(
    @SerializedName("message")
    val breedImageUrls: List<String>,
    @SerializedName("status")
    val status: String
)
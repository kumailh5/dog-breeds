package com.kumail.dogbreeds.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kumailhussain on 12/10/2021.
 */
data class BreedsListResponse(
    @SerializedName("message")
    val breedsList: Map<String, List<String>>,
    @SerializedName("status")
    val status: String
)
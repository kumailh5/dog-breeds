package com.kumail.dogbreeds.util

import com.kumail.dogbreeds.data.model.BreedsListResponse
import com.kumail.dogbreeds.data.model.ErrorResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.File

/**
 * Created by kumailhussain on 25/11/2021.
 */
class TestModelsGenerator {

    private lateinit var breedsListResponse: BreedsListResponse
    private lateinit var errorResponse: ErrorResponse

    fun generateBreedsListResponse(): BreedsListResponse {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<BreedsListResponse> = moshi.adapter(BreedsListResponse::class.java)
        val jsonString = getJson("BreedsListApiSuccessResponse.json")
        adapter.fromJson(jsonString)?.let {
            breedsListResponse = it
        }
        return breedsListResponse
    }

    fun generateBreedsListErrorResponse(): ErrorResponse {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)
        val jsonString = getJson("BreedsListApiErrorResponse.json")
        adapter.fromJson(jsonString)?.let {
            errorResponse = it
        }
        return errorResponse
    }

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}
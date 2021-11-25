package com.kumail.dogbreeds.util

import com.kumail.dogbreeds.data.model.BreedsListResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.File

/**
 * Created by kumailhussain on 25/11/2021.
 */
class TestModelsGenerator {
    private var breedsListResponse: BreedsListResponse = BreedsListResponse(emptyMap(), "")

    init {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<BreedsListResponse> = moshi.adapter(BreedsListResponse::class.java)
        val jsonString = getJson("BreedsListApiResponse.json")
        adapter.fromJson(jsonString)?.let {
            breedsListResponse = it
        }
        print("here are the $breedsListResponse")
    }

    fun generateBreedsListResponse(): BreedsListResponse {
        return breedsListResponse
    }

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}
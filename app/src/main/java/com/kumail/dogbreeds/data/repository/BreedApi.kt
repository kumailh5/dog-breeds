package com.kumail.dogbreeds.data.repository

import com.kumail.dogbreeds.data.model.BreedImagesResponse
import com.kumail.dogbreeds.data.model.BreedsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by kumailhussain on 12/10/2021.
 */
interface BreedApi {

    @GET("breeds/list/all")
    suspend fun getBreedsList(): Response<BreedsListResponse>

    @GET("breed/{breed}/images/random/10")
    suspend fun getBreedRandomImages(@Path("breed") breed: String): Response<BreedImagesResponse>

    @GET("breed/{breed}/{subBreed}/images/random/10")
    suspend fun getSubBreedRandomImages(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): Response<BreedImagesResponse>
}
package com.kumail.dogbreeds.data.repository

import com.kumail.dogbreeds.data.model.BreedImagesResponse
import com.kumail.dogbreeds.data.model.BreedsListResponse
import com.kumail.dogbreeds.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by kumailhussain on 12/10/2021.
 */
interface BreedRepository {
    suspend fun getBreedsList(): ApiResponse<BreedsListResponse>

    suspend fun getBreedRandomImages(breed: String): ApiResponse<BreedImagesResponse>

    suspend fun getSubBreedRandomImages(
        breed: String,
        subBreed: String
    ): ApiResponse<BreedImagesResponse>
}

class BreedRepositoryImpl @Inject constructor(private val breedApi: BreedApi) :
    BreedRepository {

    override suspend fun getBreedsList(): ApiResponse<BreedsListResponse> =
        withContext(Dispatchers.IO) {
            try {
                ApiResponse.create(breedApi.getBreedsList())
            } catch (e: Exception) {
                ApiResponse.ExceptionError(e)
            }
        }

    override suspend fun getBreedRandomImages(breed: String): ApiResponse<BreedImagesResponse> =
        withContext(Dispatchers.IO) {
            try {
                ApiResponse.create(breedApi.getBreedRandomImages(breed))
            } catch (e: Exception) {
                ApiResponse.ExceptionError(e)
            }
        }

    override suspend fun getSubBreedRandomImages(
        breed: String,
        subBreed: String
    ): ApiResponse<BreedImagesResponse> =
        withContext(Dispatchers.IO) {
            try {
                ApiResponse.create(
                    breedApi.getSubBreedRandomImages(
                        breed,
                        subBreed
                    )
                )
            } catch (e: Exception) {
                ApiResponse.ExceptionError(e)
            }
        }
}
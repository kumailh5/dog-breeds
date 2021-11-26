package com.kumail.dogbreeds.network

import com.kumail.dogbreeds.data.model.ErrorResponse
import com.kumail.dogbreeds.util.parseErrorJson
import retrofit2.Response

/**
 * Created by kumailhussain on 14/10/2021.
 */
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()

                if (body == null || response.code() == 204) {
                    Empty()
                } else {
                    Success(body)
                }
            } else {
                val message = response.errorBody()?.string()
                val errorMessage = if (message.isNullOrEmpty()) {
                    ErrorResponse(response.message(), "", 0)
                } else {
                    message.parseErrorJson()
                }
                NetworkError(errorMessage)
            }
        }
    }

    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Empty<T>(val data: T? = null) : ApiResponse<T>()
    data class NetworkError<T>(val errorResponse: ErrorResponse) : ApiResponse<T>()
    data class ExceptionError<T>(val errorResponse: Exception) : ApiResponse<T>()
}

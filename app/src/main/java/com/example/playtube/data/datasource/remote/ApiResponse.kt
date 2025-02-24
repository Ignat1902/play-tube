package com.example.playtube.data.datasource.remote

import com.example.playtube.utils.NetworkResult
import retrofit2.Response

abstract class ApiResponse {

    suspend fun <T> apiCall(api: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = api()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(data = body)
                } ?: return NetworkResult.Error(message = "Body is empty")
            } else {
                return NetworkResult.Error(message = "${response.message()}\n Code: ${response.code()}")
            }

        } catch (e: Exception) {
            return NetworkResult.Error(message = e.message)
        }
    }

}
package com.example.playtube.data.repository

import com.example.playtube.data.datasource.remote.ApiResponse
import com.example.playtube.data.datasource.remote.RemoteDataSource
import com.example.playtube.entities.VideoResponse
import com.example.playtube.utils.NetworkResult
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ApiResponse() {

    suspend fun getVideos(perPage: Int): NetworkResult<VideoResponse> {
        return apiCall { remoteDataSource.getVideos(perPage) }
    }
}
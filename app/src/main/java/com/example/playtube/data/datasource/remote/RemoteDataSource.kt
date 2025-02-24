package com.example.playtube.data.datasource.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val videoService: VideoService
) {
    suspend fun getVideos(perPage: Int) = videoService.getVideos(perPage = perPage)
}
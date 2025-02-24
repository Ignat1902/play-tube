package com.example.playtube.data.datasource.remote

import com.example.playtube.entities.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {
    @GET("videos/")
    suspend fun getVideos(
        @Query("per_page") perPage: Int = 5
    ): Response<VideoResponse>
}
package com.example.playtube.domain

import com.example.playtube.BuildConfig
import com.example.playtube.data.datasource.remote.VideoService
import com.example.playtube.data.repository.VideoRepository
import com.example.playtube.entities.VideoResponse
import retrofit2.Call
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend fun execute(perPage: Int) = videoRepository.getVideos(perPage)
}

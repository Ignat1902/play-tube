package com.example.playtube.entities

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("hits") val hits: List<VideoItem>
) {
    data class VideoItem(
        @SerializedName("id") val id: Int,
        @SerializedName("tags") val tags: String,
        @SerializedName("duration") val duration: Int,
        @SerializedName("videos") val videos: VideoFormats
    )
}

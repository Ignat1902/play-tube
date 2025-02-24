package com.example.playtube.entities

import com.google.gson.annotations.SerializedName

data class VideoFormats(
    @SerializedName("large")
    val large: LargeVideo
)

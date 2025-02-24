package com.example.playtube.entities

import com.google.gson.annotations.SerializedName

data class LargeVideo(
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)


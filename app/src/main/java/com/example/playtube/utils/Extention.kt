package com.example.playtube.utils

import com.example.playtube.entities.VideoResponse.VideoItem
import com.example.playtube.entities.VideoInfoUI

fun VideoItem.toExternal(): VideoInfoUI {
    return VideoInfoUI(
        id = id,
        title = tags,
        duration = duration.toString(),
        thumbnailUrl = videos.large.thumbnail,
        videoUrl = videos.large.url,
    )
}

fun List<VideoItem>.toExternal() = map(VideoItem::toExternal)
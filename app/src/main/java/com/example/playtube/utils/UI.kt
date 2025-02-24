package com.example.playtube.utils

import java.time.Duration

fun formatSecondsToTime(seconds: String): String {
    val duration = Duration.ofSeconds(seconds.toLong())
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val secs = duration.seconds % 60

    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}
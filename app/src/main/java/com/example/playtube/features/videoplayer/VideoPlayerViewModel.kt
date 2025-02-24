package com.example.playtube.features.videoplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VideoPlayerViewModel : ViewModel() {

    private val _videoUrl = MutableLiveData<String>()
    val videoUrl: LiveData<String> get() = _videoUrl

    private val _playbackPosition = MutableLiveData<Long>().apply { value = 0L }
    val playbackPosition: LiveData<Long> get() = _playbackPosition

    private val _isPlaying = MutableLiveData<Boolean>().apply { value = false }
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    fun setVideoUrl(url: String) {
        _videoUrl.value = url
    }

    fun setPlaybackPosition(position: Long) {
        _playbackPosition.value = position
    }

    fun setIsPlaying(playing: Boolean) {
        _isPlaying.value = playing
    }
    
}

class VideoListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoPlayerViewModel::class.java)) {
            return VideoPlayerViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

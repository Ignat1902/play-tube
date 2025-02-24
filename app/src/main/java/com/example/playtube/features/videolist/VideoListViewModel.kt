package com.example.playtube.features.videolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playtube.domain.GetVideosUseCase
import com.example.playtube.entities.VideoResponse
import com.example.playtube.entities.VideoInfoUI
import com.example.playtube.utils.NetworkResult
import com.example.playtube.utils.toExternal
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class VideoListViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
) : ViewModel() {

    private val _videoList = MutableLiveData<NetworkResult<VideoResponse>>()
    val videoList: LiveData<NetworkResult<VideoResponse>> = _videoList

    init {
        getVideos()
    }

    private fun getVideos() {
        _videoList.value = NetworkResult.Loading()
        viewModelScope.launch {
            _videoList.value = getVideosUseCase.execute(20)
        }

    }
}

class VideoListViewModelFactory @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoListViewModel(getVideosUseCase) as T
    }

}
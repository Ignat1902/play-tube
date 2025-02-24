package com.example.playtube.features.videoplayer

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.playtube.databinding.FragmentVideoPlayerBinding


class VideoPlayerFragment : Fragment() {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!
    private var exoPlayer: ExoPlayer? = null
    private val viewModel: VideoPlayerViewModel by viewModels { VideoListViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoUrl =
            arguments?.getString("videoUrl") ?: throw IllegalStateException("Video URL is required")
        viewModel.setVideoUrl(videoUrl)

        initializeExoPlayer()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                }
            })
    }

    private fun initializeExoPlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        val playerView = binding.playerView
        playerView.player = exoPlayer

        viewModel.videoUrl.observe(viewLifecycleOwner) { url ->
            val mediaItem = MediaItem.fromUri(url)
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.prepare()

            viewModel.playbackPosition.value?.let { position ->
                exoPlayer?.seekTo(position)
            }

            if (viewModel.isPlaying.value == true) {
                exoPlayer?.play()
            } else {
                exoPlayer?.pause()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.let { player ->
            viewModel.setPlaybackPosition(player.currentPosition)
            viewModel.setIsPlaying(player.isPlaying)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.let { player ->
            viewModel.setPlaybackPosition(player.currentPosition)
            viewModel.setIsPlaying(player.isPlaying)
        }
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> enterFullScreen()
            Configuration.ORIENTATION_PORTRAIT -> exitFullScreen()
            else -> {}
        }
    }

    private fun enterFullScreen() {
        activity?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        val params = binding.playerView.layoutParams as ViewGroup.MarginLayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        params.setMargins(0, 0, 0, 0)
        binding.playerView.layoutParams = params
    }

    private fun exitFullScreen() {
        // Показываем системные элементы
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

        // Возвращаем PlayerView к обычным размерам
        val params = binding.playerView.layoutParams as ViewGroup.MarginLayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.setMargins(16, 16, 16, 16) // Пример отступов
        binding.playerView.layoutParams = params
    }

    companion object {
        fun newInstance(videoUrl: String): VideoPlayerFragment {
            val fragment = VideoPlayerFragment()
            val args = Bundle()
            args.putString("videoUrl", videoUrl)
            fragment.arguments = args
            return fragment
        }
    }
}

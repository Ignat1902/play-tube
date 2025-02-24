package com.example.playtube.features.videolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playtube.R
import com.example.playtube.appComponent
import com.example.playtube.databinding.FragmentVideoListBinding
import com.example.playtube.domain.GetVideosUseCase
import com.example.playtube.features.videoplayer.VideoPlayerFragment
import com.example.playtube.utils.NetworkResult
import com.example.playtube.utils.toExternal
import javax.inject.Inject

class VideoListFragment : Fragment(), Listener {

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    @Inject
    lateinit var videosUseCase: GetVideosUseCase
    private val viewModel: VideoListViewModel by viewModels {
        VideoListViewModelFactory(videosUseCase)
    }

    private var _binding: FragmentVideoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.videoListRecyclerView
        val progressBar = binding.progressBar
        val errorTextView = binding.errorTextView
        val pullToRefresh = binding.pullToRefresh

        val adapter = VideoListAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false
        )
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(VideoItemDecoration())

        viewModel.videoList.observe(viewLifecycleOwner) { state ->
            val currentState = state ?: NetworkResult.Loading() // По умолчанию Loading

            when (currentState) {
                is NetworkResult.Success -> {
                    progressBar.visibility = View.GONE
                    errorTextView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    val newList = currentState.data?.hits?.toExternal() ?: emptyList()
                    adapter.updateList(newList)
                }

                is NetworkResult.Error -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = currentState.message ?: "Ошибка загрузки!"
                }

                is NetworkResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    errorTextView.visibility = View.GONE
                }
            }
        }

        pullToRefresh.setOnRefreshListener {
            val newList = viewModel.videoList.value?.data?.hits?.toExternal()?.shuffled()
            newList?.let { adapter.updateList(it) }
            pullToRefresh.isRefreshing = false
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickToVideo(videoUrl: String) {
        val playerFragment = VideoPlayerFragment.newInstance(videoUrl)
        if (parentFragmentManager.findFragmentById(R.id.fragment_container_view) !is VideoPlayerFragment) {
            Log.d("test", "${parentFragmentManager.backStackEntryCount}")
            parentFragmentManager.backStackEntryCount
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, playerFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
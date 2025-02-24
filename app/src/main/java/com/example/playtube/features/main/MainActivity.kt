package com.example.playtube.features.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playtube.R
import com.example.playtube.databinding.ActivityMainBinding
import com.example.playtube.features.videolist.VideoListFragment
import com.example.playtube.features.videoplayer.VideoPlayerFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val videoListFragment = VideoListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, videoListFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
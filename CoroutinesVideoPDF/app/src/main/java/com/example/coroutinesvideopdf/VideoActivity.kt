package com.example.coroutinesvideopdf

import android.os.Bundle
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView = findViewById(R.id.videoView)
        progressBar = findViewById(R.id.progressBar)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)


        progressBar.visibility = ProgressBar.VISIBLE

        // Set up video loading using coroutine
        CoroutineScope(Dispatchers.IO).launch {
            val videoUrl = loadVideoUrl()

            withContext(Dispatchers.Main) {
                playVideo(videoUrl)
            }
        }

        // Listen for when the video is ready to be played
        videoView.setOnPreparedListener {

            progressBar.visibility = ProgressBar.GONE
        }
    }

    private suspend fun loadVideoUrl(): String {
        delay(1000)
        return "https://shattereddisk.github.io/rickroll/rickroll.mp4"  // Replace with your video URL
    }


    private fun playVideo(videoUrl: String) {
        videoView.setVideoPath(videoUrl)
        videoView.start()
    }
}
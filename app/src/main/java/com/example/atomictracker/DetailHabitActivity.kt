package com.example.atomictracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.atomictracker.databinding.ActivityDetailHabitBinding
import com.example.atomictracker.databinding.ActivityMainBinding
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class DetailHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHabitBinding

    val VIDEO_ID = "XUlrAKRuE68"
    val YOUTUBE_API_KEY = "AIzaSyCJVX7XKr4wqFPfsolE5yhvv31POpFela8"

    private lateinit var youTubePlayer: YouTubePlayerView
    private lateinit var youtubePlayerInit : YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setValues()
    }

    private fun setValues(){
        binding.nameDetailTextView.text = intent.getStringExtra("nombre")
        binding.frequencyDetailTextView.text = intent.getStringExtra("frecuencia")
        binding.notificationDetailTextView.text = intent.getStringExtra("notificacion")
        binding.hourDetailTextView.text = intent.getStringExtra("hora")
        binding.dateDetailTextView.text = intent.getStringExtra("inicio")
        binding.playButton.setOnClickListener{
            youTubePlayer.initialize(YOUTUBE_API_KEY, youtubePlayerInit)
        }

        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(VIDEO_ID)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext, "Fallo", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
package mx.tec.atomictracker

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {


    lateinit var videoActivity: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoActivity = findViewById(R.id.videoView)
        //videoActivity.setVideoPath("https://www.youtube.com/watch?v=BllAH1xM2gs")
        videoActivity.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=dwALVhaZkUY"))
        videoActivity.setMediaController(MediaController(this))
        videoActivity.start()

    }
}
package com.acsoft.mumovie.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.acsoft.mumovie.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_movie.*


class MovieActivity : YouTubeBaseActivity() {
    

    lateinit var  youtubePlayerInit: YouTubePlayer.OnInitializedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)



        initUI("LQXZiYL0VsI",resources.getString(R.string.developer_key))


    }

    private fun initUI(videoId:String,apiKey:String) {
        youtubePlayerInit =  object  : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
                p1?.loadVideo(videoId)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                Toast.makeText(applicationContext,"sdfsdf",Toast.LENGTH_SHORT).show()
            }

        }
        youtubePlayer.initialize(apiKey,youtubePlayerInit)

    }
}

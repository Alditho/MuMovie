package com.acsoft.mumovie.activities

import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.acsoft.mumovie.R
import com.acsoft.mumovie.interfaces.ApiInterface
import com.acsoft.mumovie.models.Video
import com.acsoft.mumovie.models.VideoList
import com.acsoft.mumovie.utils.ApiMovie
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieActivity : YouTubeBaseActivity() {

    //https://api.themoviedb.org/3/movie/424783/videos?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US


    lateinit var  youtubePlayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieId:Int = intent.getIntExtra("movie",0)
        val title:String = intent.getStringExtra("title")
        var average = intent.getDoubleExtra("average",0.0)
        val overview:String = intent.getStringExtra("overview")

        getMovieVideo(movieId)

        val titleTextView:TextView = findViewById(R.id.textViewTitle)
        val overviewTextView:TextView = findViewById(R.id.textViewOverView)
        val ratingTextView:TextView = findViewById(R.id.textViewRating)


        titleTextView.text = title
        ratingTextView.text = average.toString()+"/10"
        overviewTextView.text = overview



    }


    //obtener video trailer de pelicula
   private fun getMovieVideo(videoId: Int){

        var apiInterface: ApiInterface = ApiMovie().getApiMovie()!!.create(ApiInterface::class.java)
        //Recibimos video
        apiInterface.getVideoMovie(videoId).enqueue(object : Callback<VideoList>{

            override fun onResponse(call: Call<VideoList>, response: Response<VideoList>) {

                if(response.isSuccessful){

                    val video: List<Video> = response.body()!!.videos
                    if(!video.isNullOrEmpty())
                        initUI(video[0].key,resources.getString(R.string.developer_key))

                }else{
                    Toast.makeText(applicationContext,resources.getString(R.string.error_server),Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<VideoList>, t: Throwable) {
                Toast.makeText(applicationContext,resources.getString(R.string.error_server),Toast.LENGTH_SHORT).show()
            }


        })
    }



    private fun initUI(videoId:String,apiKey:String) { //obtener video y reproducirlo
        youtubePlayerInit =  object  : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
                p1?.loadVideo(videoId)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                Log.e("VIDEO","ERROR")
            }

        }
        youtubePlayer.initialize(apiKey,youtubePlayerInit)

    }



}

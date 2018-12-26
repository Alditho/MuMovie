package com.acsoft.mumovie.interfaces



import com.acsoft.mumovie.models.MovieList
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("now_playing?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&page=1&region=US")
    fun getNowPlaying() : Call<MovieList>

}

package com.acsoft.mumovie.interfaces



import com.acsoft.mumovie.models.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("now_playing?api_key=9ac52d936fbee6f02ba75934a83b23af&language=es-MX&page=1&region=US")
    fun getNowPlaying() : Call<MovieList>

    @GET("now_playing?api_key=9ac52d936fbee6f02ba75934a83b23af&language=es-MX&region=US")
    fun getNowPlayingByPage(@Query("page") page: Int) : Call<MovieList>


}

package com.acsoft.mumovie.interfaces



import com.acsoft.mumovie.models.MovieList
import com.acsoft.mumovie.models.Video
import com.acsoft.mumovie.models.VideoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("3/movie/now_playing?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&page=1&region=US")
    fun getNowPlaying() : Call<MovieList>

    @GET("3/movie/now_playing?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&region=US")
    fun getNowPlayingByPage(@Query("page") page: Int) : Call<MovieList>

    @GET("3/movie/{id}/videos?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US")
    fun getVideoMovie(@Path("id") id:Int) : Call<VideoList>

    @GET("3/movie/popular?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&page=1&region=US")
    fun getPopularMovie() : Call<MovieList>

    @GET("3/movie/popular?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&region=US")
    fun getPopularMovieByPage(@Query("page") page: Int) : Call<MovieList>

    @GET("3/movie/top_rated?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&page=1&region=US")
    fun getTopRatedMovie() : Call<MovieList>

    @GET("3/movie/top_rated?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&region=US")
    fun getTopRatedMovieByPage(@Query("page") page: Int) : Call<MovieList>

}

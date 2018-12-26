package com.acsoft.mumovie.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.acsoft.mumovie.utils.ApiMovie
import com.acsoft.mumovie.R
import com.acsoft.mumovie.adapters.ReleaseAdapter
import com.acsoft.mumovie.interfaces.ApiInterface
import com.acsoft.mumovie.models.Movie
import com.acsoft.mumovie.models.MovieList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReleaseFragment : Fragment() {

    var recycler:RecyclerView? = null  //recyclerview
    var adapter:ReleaseAdapter? = null //adaptador personalizado
    var layoutManager:RecyclerView.LayoutManager? = null
    var releaseList = ArrayList<Movie>()


    //https://api.themoviedb.org/3/movie/now_playing?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&page=1&region=US

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_release,container,false)


        getReleaseMovies(view)
        buildRecyclerView(view)








        return view

    }


    fun getReleaseMovies(view: View){
        var apiInterface: ApiInterface = ApiMovie().getApiMovie()!!.create(ApiInterface::class.java)
        //Recibimos todos los posts
        apiInterface.getNowPlaying().enqueue(object: Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {

                if(response.isSuccessful){//response ok
                    var movies: List<Movie> = response.body()!!.movies

                    for (movie: Movie in movies.iterator()){
                        releaseList.add(Movie(movie.id,movie.title))
                    }

                    adapter = ReleaseAdapter(view.context,releaseList)
                    recycler?.adapter = adapter

                }else{
                    Toast.makeText(context,"Problemas al cargar datos",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Toast.makeText(context,"fallo",Toast.LENGTH_SHORT).show()
            }

        })
    }

    //construir recyclerview
    fun buildRecyclerView(view:View){
        recycler = view.findViewById(R.id.recyclerviewRelease)
        recycler?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(view.context)
        recycler?.layoutManager = layoutManager



    }




}
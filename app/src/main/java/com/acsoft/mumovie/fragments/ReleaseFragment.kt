package com.acsoft.mumovie.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.acsoft.mumovie.utils.ApiMovie
import com.acsoft.mumovie.R
import com.acsoft.mumovie.activities.MovieActivity
import com.acsoft.mumovie.adapters.ReleaseAdapter
import com.acsoft.mumovie.interfaces.ApiInterface
import com.acsoft.mumovie.interfaces.ClickListener
import com.acsoft.mumovie.models.Movie
import com.acsoft.mumovie.models.MovieList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReleaseFragment : Fragment() {  //Muestra peliculas que están en cines

    var recycler:RecyclerView? = null  //recyclerview
    var adapter:ReleaseAdapter? = null //adaptador personalizado
    var layoutManager:RecyclerView.LayoutManager? = null //manager
    var releaseList = ArrayList<Movie>() //lista de peliculas
    var loading = false


    //https://api.themoviedb.org/3/movie/now_playing?api_key=9ac52d936fbee6f02ba75934a83b23af&language=en-US&page=1&region=US
    //https://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_release,container,false)


        getReleaseMovies()
        buildRecyclerView(view)

        return view

    }


    //obtener peliculas qué están en cines en estos momentos
    fun getReleaseMovies(){

        var apiInterface: ApiInterface = ApiMovie().getApiMovie()!!.create(ApiInterface::class.java)
        //Recibimos todos los posts
        apiInterface.getNowPlaying().enqueue(object: Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {

                if(response.isSuccessful){//response ok

                    var movies: List<Movie> = response.body()!!.movies //obtener lista de peliculas

                    for (movie: Movie in movies.iterator()){//llenar peliculas en arraylist
                        releaseList.add(Movie(movie.id,movie.title,movie.overview,movie.posterPath))
                    }

                    recycler?.adapter = adapter //adaptador de rv

                }else{
                    Toast.makeText(context,resources.getString(R.string.error_server),Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Toast.makeText(context,resources.getString(R.string.error_server),Toast.LENGTH_SHORT).show()
            }

        })
    }

    //construir recyclerview
    fun buildRecyclerView(view:View){

        onClick(view) //click a elemento de la lista

        recycler = view.findViewById(R.id.recyclerviewRelease)
        recycler?.setHasFixedSize(true)

        layoutManager = GridLayoutManager(context,2)
        recycler?.layoutManager = layoutManager

        onScroll()

    }

    fun onClick(view: View){ //click elemento de recyclerview
        adapter = ReleaseAdapter(view.context,releaseList,object :ClickListener{
            override fun onClick(view: View, index: Int) {
                Toast.makeText(context,releaseList.get(index).title,Toast.LENGTH_LONG).show()
                val intent = Intent(context,MovieActivity::class.java)
                startActivity(intent)

            }

        })
    }


    fun onScroll(){ //al hacer scroll
        recycler?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recycler!!, dx, dy)
                val visibleItemCount = (layoutManager as GridLayoutManager).childCount
                val totalItemCount = (layoutManager as GridLayoutManager).itemCount
                val firstVisible = (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                if (!loading && (visibleItemCount + firstVisible) >= totalItemCount) {
                    loading = true
                    releaseList.add(Movie(50,"Titulo","overview","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"))
                    releaseList.add(Movie(50,"Titulo","overview","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"))
                    releaseList.add(Movie(50,"Titulo","overview","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"))
                    releaseList.add(Movie(50,"Titulo","overview","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"))
                    releaseList.add(Movie(50,"Titulo","overview","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"))
                    releaseList.add(Movie(50,"Titulo","overview","/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"))
                    adapter?.notifyDataSetChanged()
// recycler?.adapter = adapter
                    //Toast.makeText(context,"hay que cargar mas items culero",Toast.LENGTH_SHORT).show()
                    //loading = false
                }
            }
        })
    }



}
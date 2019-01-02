package com.acsoft.mumovie.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.acsoft.mumovie.R
import com.acsoft.mumovie.activities.MovieActivity
import com.acsoft.mumovie.adapters.ReleaseAdapter
import com.acsoft.mumovie.interfaces.ApiInterface
import com.acsoft.mumovie.interfaces.ClickListener
import com.acsoft.mumovie.models.Movie
import com.acsoft.mumovie.models.MovieList
import com.acsoft.mumovie.utils.ApiMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RateFragment : Fragment() { //PELICULAS MEJOR VOTADAS


    var recycler: RecyclerView? = null  //recyclerview
    var adapter: ReleaseAdapter? = null //adaptador personalizado
    var layoutManager: RecyclerView.LayoutManager? = null //manager
    var moviesList = ArrayList<Movie>() //lista de peliculas

    var loading:Boolean = false //guarda estado de scroll, cargar o no datos nuevos de servidor
    var numberPage:Int = 1
    var totalPages:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View= inflater.inflate(R.layout.fragment_rate,container,false)

        getTopRatedMovies()
        buildRecyclerView(view)


        return view
    }

    //obtener primera pagina de peliculas
    fun getTopRatedMovies(){

        var apiInterface: ApiInterface = ApiMovie().getApiMovie()!!.create(ApiInterface::class.java)
        //Recibimos todos los posts
        apiInterface.getTopRatedMovie().enqueue(object: Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {

                if(response.isSuccessful){//response ok

                    var movies: List<Movie> = response.body()!!.movies //obtener lista de peliculas

                    for (movie: Movie in movies.iterator()){//llenar peliculas en arraylist
                        if(movie.posterPath.isNullOrEmpty()){
                            moviesList.add(Movie(movie.id,movie.title,movie.overview,movie.average,"notfound"))
                        }else{
                            moviesList.add(Movie(movie.id,movie.title,movie.overview,movie.average,movie.posterPath))
                        }
                    }

                    recycler?.adapter = adapter //adaptador de rv

                    //sumar valor de pagina siguiente
                    numberPage=response.body()!!.page+1
                    totalPages=response.body()!!.totalPages //obtener total de paginas


                }else{
                    Toast.makeText(context,resources.getString(R.string.error_server), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Toast.makeText(context,resources.getString(R.string.error_server), Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun getTopRatedMoviesByPage(page:Int,numberPages:Int){ //obtener peliculas filtradas por pagina

        if(page>numberPages){
            Toast.makeText(context,resources.getString(R.string.no_more), Toast.LENGTH_SHORT).show()
        }else{
            var apiInterface: ApiInterface = ApiMovie().getApiMovie()!!.create(ApiInterface::class.java)
            //Recibimos todos los posts
            apiInterface.getTopRatedMovieByPage(page).enqueue(object: Callback<MovieList> {
                override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {

                    if(response.isSuccessful){//response ok

                        var movies: List<Movie> = response.body()!!.movies //obtener lista de peliculas

                        for (movie: Movie in movies.iterator()){//llenar peliculas en arraylist

                            if(movie.posterPath.isNullOrEmpty()){
                                moviesList.add(Movie(movie.id,movie.title,movie.overview,movie.average,"notfound"))
                            }else{
                                moviesList.add(Movie(movie.id,movie.title,movie.overview,movie.average,movie.posterPath))
                            }
                        }//termina for



                        adapter?.notifyDataSetChanged() //informar de cambios al adaptador
                        numberPage=response.body()!!.page+1 //numero nuevo de pagina
                        totalPages=response.body()!!.totalPages //paginas totales

                        loading = false

                    }else{
                        Toast.makeText(context,resources.getString(R.string.error_server), Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    Toast.makeText(context,resources.getString(R.string.error_server), Toast.LENGTH_SHORT).show()
                }

            })
        }//termina else


    }

    //construir recyclerview
    fun buildRecyclerView(view:View){

        onClick(view) //click a elemento de la lista

        recycler = view.findViewById(R.id.recyclerviewRate)
        recycler?.setHasFixedSize(true)

        layoutManager = GridLayoutManager(context,2)
        recycler?.layoutManager = layoutManager

        onScroll()

    }

    fun onClick(view: View){ //click elemento de recyclerview
        adapter = ReleaseAdapter(view.context,moviesList,object : ClickListener {
            override fun onClick(view: View, index: Int) {
                val intent = Intent(context, MovieActivity::class.java)
                intent.putExtra("movie",moviesList[index].id)
                intent.putExtra("title", moviesList[index].title)
                intent.putExtra("average",moviesList[index].average)
                intent.putExtra("overview", moviesList[index].overview)
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
                    getTopRatedMoviesByPage(numberPage,totalPages) //cargar siguiente pagina
                }

            }
        })
    } //al hacer scroll cargar mas peliculas



}
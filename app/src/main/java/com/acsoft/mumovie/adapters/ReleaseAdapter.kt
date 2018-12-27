package com.acsoft.mumovie.adapters

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.acsoft.mumovie.models.Movie
import com.acsoft.mumovie.R
import com.acsoft.mumovie.interfaces.ClickListener
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ReleaseAdapter(var context: Context,items:ArrayList<Movie>, var listener: ClickListener): RecyclerView.Adapter<ReleaseAdapter.ViewHolder>() {

    var items : ArrayList<Movie>? =null
    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.template_release,parent,false)
        val  viewHolder = ViewHolder(view,listener)
        return  viewHolder
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        val url = "https://image.tmdb.org/t/p/w185/"
        Picasso.with(context).load(url+item?.posterPath.toString()).into(holder.image)
    }




    class  ViewHolder(view:View, listener: ClickListener): RecyclerView.ViewHolder(view),View.OnClickListener{
        var view = view
        var image:ImageView? = null
        var listener: ClickListener? = null

        init {
            image = view.findViewById(R.id.imageViewMovieImage)
            this.listener = listener
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }
    }
}
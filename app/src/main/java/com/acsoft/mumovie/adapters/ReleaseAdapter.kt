package com.acsoft.mumovie.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.acsoft.mumovie.models.Movie
import com.acsoft.mumovie.R

class ReleaseAdapter(var context: Context,items:ArrayList<Movie>): RecyclerView.Adapter<ReleaseAdapter.ViewHolder>() {

    var items : ArrayList<Movie>? =null
    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.template_release,parent,false)
        val  viewHolder = ViewHolder(view)
        return  viewHolder
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.title?.text= item?.title.toString()
    }



    class  ViewHolder(view:View): RecyclerView.ViewHolder(view){
        var view = view
        var title:TextView? = null

        init {
            title = view.findViewById(R.id.textView)
        }
    }
}
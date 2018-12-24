package com.acsoft.mumovie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acsoft.mumovie.Adapters.ReleaseAdapter
import com.acsoft.mumovie.POJO.Movie

class ReleaseFragment : Fragment() {

    var list:RecyclerView? = null
    var adapter:ReleaseAdapter? = null
    var layoutManager:RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_release,container,false)


        val releaseList = ArrayList<Movie>()

        releaseList.add(Movie(1,"Aquaman"))
        releaseList.add(Movie(2,"bumblebee"))

        list = view.findViewById(R.id.recyclerviewRelease)
        list?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(view.context)
        list?.layoutManager = layoutManager

        adapter = ReleaseAdapter(view.context,releaseList)
        list?.adapter = adapter

        return view
        //return super.onCreateView(inflater, container, savedInstanceState)

    }
}
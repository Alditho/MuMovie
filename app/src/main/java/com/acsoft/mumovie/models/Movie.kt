package com.acsoft.mumovie.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") var id:Int=0,
    @SerializedName("title") var title:String,
    var overview:String,
    @SerializedName("vote_average") var average:Double,
    @SerializedName("poster_path") var posterPath:String)

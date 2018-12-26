package com.acsoft.mumovie.models

import com.google.gson.annotations.SerializedName

data class Movie(@SerializedName("id") var id:Int=0, @SerializedName("title") var title:String) {
}
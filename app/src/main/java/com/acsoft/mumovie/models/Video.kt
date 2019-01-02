package com.acsoft.mumovie.models

import com.google.gson.annotations.SerializedName

class Video (@SerializedName("id") var id:String,@SerializedName("key") var key:String, @SerializedName("name") var videoName:String)

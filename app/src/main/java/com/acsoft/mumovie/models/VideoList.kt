package com.acsoft.mumovie.models

import com.google.gson.annotations.SerializedName

class VideoList(@SerializedName("results") var videos:List<Video>)

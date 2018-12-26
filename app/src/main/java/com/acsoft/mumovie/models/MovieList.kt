package com.acsoft.mumovie.models

import com.google.gson.annotations.SerializedName

data class MovieList(@SerializedName("results") var movies:List<Movie>)
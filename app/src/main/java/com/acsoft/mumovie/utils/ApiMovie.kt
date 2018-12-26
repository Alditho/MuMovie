package com.acsoft.mumovie.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiMovie {

    var BASE_URL: String = "https://api.themoviedb.org/3/movie/"
    var retrofit: Retrofit? = null

    fun getApiMovie(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

}
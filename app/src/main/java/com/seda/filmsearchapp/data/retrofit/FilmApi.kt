package com.seda.filmsearchapp.data.retrofit

import com.seda.filmsearchapp.common.util.Constants
import com.seda.filmsearchapp.data.model.FilmResult
import com.seda.filmsearchapp.data.model.Search
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmApi {

    @GET("?apikey=fbdd89ef")
    suspend fun getFilm(
        @Query("s") search:String
    ): Response<FilmResult>

    @GET("?apikey=fbdd89ef")
   suspend fun getMovie(
        @Query("i") movieId: String,
                 ): Response<FilmResult>
}
package com.seda.filmsearchapp.data.repository

import android.util.Log
import com.seda.filmsearchapp.common.util.Constants
import com.seda.filmsearchapp.common.util.Resource
import com.seda.filmsearchapp.data.model.FilmResult
import com.seda.filmsearchapp.data.model.Search
import com.seda.filmsearchapp.data.retrofit.FilmApi
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject


class SearchScreenRepository @Inject constructor(private val filmApi: FilmApi){
suspend fun getFilms(search:String): Resource<FilmResult> {



    return try {
        val response =  filmApi.getFilm(search)
        if (response.isSuccessful) {
            response.body()?.let {
                return@let Resource.success(it)
            } ?: Resource.error("Error",null)
        } else {
            Resource.error("Error",null)
        }
    } catch (e: Exception) {
        Resource.error("No data!",null)
    }

}


}
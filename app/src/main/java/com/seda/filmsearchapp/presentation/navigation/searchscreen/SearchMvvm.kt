package com.seda.filmsearchapp.presentation.navigation.searchscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seda.filmsearchapp.common.util.Resource
import com.seda.filmsearchapp.data.model.DetailResponse
import com.seda.filmsearchapp.data.model.FilmResult
import com.seda.filmsearchapp.data.model.Search
import com.seda.filmsearchapp.data.repository.SearchScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMvvm @Inject constructor(private val repository: SearchScreenRepository):ViewModel()  {
  private  val _countries = MutableLiveData<Resource<FilmResult>>()
    val responseTvShow: LiveData<Resource<FilmResult>>
        get() = _countries

    private  val _movies = MutableLiveData<Resource<DetailResponse>>()
    val responsemovies: LiveData<Resource<DetailResponse>>
        get() = _movies

@SuppressLint("SuspiciousIndentation")
fun loadfilm(kelime:String){
     viewModelScope.launch {
     val deger =  repository.getFilms(kelime)

               _countries.value = deger
     }
    }


    fun getmovies(movieid:String){

        viewModelScope.launch {

            val deger1 = repository.getDetail(movieid)
            _movies.value =deger1
        }
    }
  }
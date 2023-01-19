package com.seda.filmsearchapp.presentation.navigation.filmdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.seda.filmsearchapp.R
import com.seda.filmsearchapp.common.util.Status
import com.seda.filmsearchapp.databinding.FragmentFilmDetailBinding
import com.seda.filmsearchapp.presentation.navigation.searchscreen.SearchMvvm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmDetailFragment : Fragment() {
 private lateinit var binding:FragmentFilmDetailBinding
    val bundle:FilmDetailFragmentArgs by navArgs()
    private  val filmmvvm: SearchMvvm by viewModels()
  lateinit var gelenid:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmDetailBinding.inflate(layoutInflater,container,false)
return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       gelenid = bundle.getid
filmmvvm.getmovies(gelenid)
        Log.e("gelen","${gelenid}")
        observefavorites()

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController,appBarConfig)
        binding.toolbar.title = "Movie"
    }
    private fun observefavorites() {
        filmmvvm.responsemovies.observe(viewLifecycleOwner, Observer {

            when(it.status){

                Status.SUCCESS ->{
                  Glide.with(requireActivity())
                       .load(it.data?.Poster)
                      .into(binding.filmresim)
                     binding.language.text = it.data?.Language
                     binding.year.text = it.data?.Year
                     binding.filmname.text = it.data?.Title
                     binding.director.text = it.data?.Director
                     binding.actors.text = it.data?.Actors
                     binding.genre.text = it.data?.Genre
                     binding.topic.text = it.data?.Plot



                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                }else->{
            }

            }
        })
    }


}
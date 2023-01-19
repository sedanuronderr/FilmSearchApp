package com.seda.filmsearchapp.presentation.navigation.searchscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.seda.filmsearchapp.common.util.Status
import com.seda.filmsearchapp.data.model.Search
import com.seda.filmsearchapp.databinding.FragmentSearchScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchScreenFragment : Fragment() {
private lateinit var binding:FragmentSearchScreenBinding
private  val filmmvvm:SearchMvvm by viewModels()
    var gelenDeger :String=""
private lateinit var filmadapter:SearchRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSearchScreenBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        observefavorites()


        with(binding){
            searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null) {
                        filmmvvm.loadfilm(p0)
                    }

                    binding.LoadingBar.visibility = View.VISIBLE



             return false
                }

            })

        }

        filmmvvm.responseTvShow.observe(viewLifecycleOwner, Observer {
            it.data?.Search?.map {
                gelenDeger = it.imdbID
            }
        })
    }
    private fun setupRecyclerView(view: View) {

        binding.recyclerView.apply {

            layoutManager = GridLayoutManager(requireActivity(),2)
            filmadapter = SearchRecyclerView()

            filmadapter.onLongClickListener = {
                val gecis = SearchScreenFragmentDirections.actionSearchScreenFragmentToFilmDetailFragment(gelenDeger)
                Navigation.findNavController(view).navigate(gecis)

            }
            adapter = filmadapter
            setHasFixedSize(true)
        }

    }

    private fun observefavorites() {
    filmmvvm.responseTvShow.observe(viewLifecycleOwner, Observer {

             when(it.status){

                 Status.SUCCESS ->{
                     val bilgi = it.data?.Search
                     if (bilgi != null) {
                         filmadapter.setCategory(bilgi)
                     }
                     binding.LoadingBar.visibility = View.GONE

                 }
                 Status.ERROR -> {
                     Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                         .show()
                     binding.errortext.visibility =View.VISIBLE
                 }else->{
                 binding.LoadingBar.visibility = View.GONE
                 }

             }
            })
    }

}
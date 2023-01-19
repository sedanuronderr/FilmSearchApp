package com.seda.filmsearchapp.presentation.navigation.searchscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seda.filmsearchapp.data.model.FilmResult
import com.seda.filmsearchapp.data.model.Search
import com.seda.filmsearchapp.databinding.FilmCardBinding
import io.reactivex.Single

class SearchRecyclerView:RecyclerView.Adapter<SearchRecyclerView.SearchViewHolder>(),Filterable {
 var onLongClickListener:((Search)->Unit)?=null
    private val filmList = ArrayList<Search>()
    var filmFilterList = ArrayList<Search>()
    class SearchViewHolder(val binding: FilmCardBinding):RecyclerView.ViewHolder(binding.root) {


    }

    fun setCategory(list: List<Search>){
filmList.clear()
        filmList.addAll(list)
        notifyDataSetChanged()
    }
    init {
        filmFilterList = filmList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
     return  SearchViewHolder(FilmCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val film = filmFilterList[position]
        holder.binding.filmismi.text= film.Title
        Glide.with(holder.itemView)
            .load(film.Poster)
            .into(holder.binding.filmimage)
       holder.itemView.setOnClickListener {
           onLongClickListener.let {
               if (it != null) {
                   it(filmFilterList[position])
               }
           }
       }

    }

    override fun getItemCount(): Int {
return filmFilterList.size

    }

    override fun getFilter(): Filter {
        return object :Filter(){
            @SuppressLint("SuspiciousIndentation")
            override fun performFiltering(constarint: CharSequence?): FilterResults {
            val charSearch = constarint.toString().lowercase()
                if(charSearch.isEmpty()){
                    filmFilterList = filmList
                }else{
                    val resultList=ArrayList<Search
                            >()
                    for(row in filmList){
                    row.Title.let{ title->
                        if(title.lowercase().contains(charSearch)){
                            resultList.add(row)
                        }

                    }
                    }
                    filmFilterList= resultList

                }
                val filterResults = FilterResults()
                filterResults.values = filmFilterList

                return  filterResults

            }

            override fun publishResults(constraint: CharSequence?, p1: FilterResults?) {
                filmFilterList  = p1?.values as ArrayList<Search>



            }

        }

    }


}
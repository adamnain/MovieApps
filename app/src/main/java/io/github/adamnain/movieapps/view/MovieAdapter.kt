package io.github.adamnain.movieapps.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.adamnain.movieapps.R
import io.github.adamnain.movieapps.databinding.ItemMovieBinding
import io.github.adamnain.movieapps.model.Movie

class MovieListAdapter(val movieList: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(), MovieListener {


    fun updateMovieList(newDogsList: List<Movie>) {
        movieList.clear()
        movieList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemMovieBinding>(inflater, R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.movie = movieList[position]
        holder.view.listener = this
    }

    class MovieViewHolder(var view: ItemMovieBinding) : RecyclerView.ViewHolder(view.root)


    override fun onMovieClicked(v: View) {
        Log.d("Adamnain Log: ", "Item Clicked")
        // on click
    }


}
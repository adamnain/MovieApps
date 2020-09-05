package io.github.adamnain.movieapps.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import io.github.adamnain.movieapps.R
import io.github.adamnain.movieapps.databinding.ItemMovieBinding
import io.github.adamnain.movieapps.model.Movie
import io.github.adamnain.movieapps.viewmodel.MoviesViewModel
import androidx.fragment.app.FragmentActivity
import io.github.adamnain.movieapps.databinding.FragmentMoviesBinding


class MovieListAdapter(val movieList: ArrayList<Movie>, val fragment: MoviesFragment) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    //private lateinit var moviesViewModel: MoviesViewModel


    fun updateMovieList(newDogsList: List<Movie>) {
        movieList.clear()
        movieList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemMovieBinding>(inflater, R.layout.item_movie, parent, false)

        return MovieViewHolder(binding, fragment)
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.movie = movieList[position]
        holder.view.view =  holder.fragment
    }

    class MovieViewHolder(var view: ItemMovieBinding, var fragment: MoviesFragment  ) : RecyclerView.ViewHolder(view.root)




}
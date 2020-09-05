package io.github.adamnain.movieapps.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.adamnain.movieapps.R
import io.github.adamnain.movieapps.databinding.FragmentMoviesBinding
import io.github.adamnain.movieapps.databinding.ItemMovieBinding
import io.github.adamnain.movieapps.model.Movie
import io.github.adamnain.movieapps.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {
    private lateinit var moviesViewModel: MoviesViewModel
    private var movieListAdapter = MovieListAdapter(arrayListOf(), this)
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movies,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        //binding.viewModel = moviesViewModel
        moviesViewModel.fetchFromRemote()


        rv_movie.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieListAdapter
        }

        refresh_layout.setOnRefreshListener {
            moviesViewModel.fetchFromDatabase()
            refresh_layout.isRefreshing = false
        }
        observeViewModel()
    }


    fun observeViewModel() {
        moviesViewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                rv_movie.visibility = View.VISIBLE
                movieListAdapter.updateMovieList(movies)
            }
        })

        moviesViewModel.moviesLoadError.observe(this, Observer { isError ->
            isError?.let {
                tv_list_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        moviesViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                progress_movie.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tv_list_error.visibility = View.GONE
                    rv_movie.visibility = View.GONE
                }
            }
        })


    }

    fun createDialog(movie: Movie) {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Favorite Movie")
        if(movie.isFavorited == 0){
            builder.setMessage("Is your favorite movie?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                moviesViewModel.storeFavorite(movie)
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
        } else {
            builder.setMessage("Delete from your favorite?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                moviesViewModel.deleteFovorite(movie)
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
        }


        builder.show()
    }



}
package io.github.adamnain.movieapps.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.adamnain.movieapps.api.MoviesApiService
import io.github.adamnain.movieapps.db.MovieDatabase
import io.github.adamnain.movieapps.model.Movie
import io.github.adamnain.movieapps.model.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : BaseViewModel(application) {


    private val momvieService = MoviesApiService()
    private val disposable = CompositeDisposable()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _moviesLoadError = MutableLiveData<Boolean>()
    val moviesLoadError: LiveData<Boolean> get() = _moviesLoadError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun fetchFromRemote() {
        _loading.value = true
        _moviesLoadError.value = false
        disposable.add(
            momvieService.getMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>() {
                    override fun onSuccess(movieResponse: MovieResponse) {
//                        _movies.value = movieResponse.results as List<Movie>?
//                        _loading.value = false
                        checkFavorite(movieResponse.results as List<Movie>?)
                        _loading.value = false
                        //Toast.makeText(getApplication(), "On Success", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        _moviesLoadError.value = true
                        _loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    fun fetchFromDatabase() {
        _loading.value = true
        _moviesLoadError.value = false
        viewModelScope.launch {
            val movies = MovieDatabase(getApplication()).movieDao().getAllMovies()
            _movies.value = movies
            _loading.value = false
            Toast.makeText(getApplication(), "Movies retrieved from database", Toast.LENGTH_SHORT)
                .show()
        }

    }


    fun storeFavorite(movie: Movie) {
        launch {
            MovieDatabase(getApplication()).movieDao().insertMovie(movie)

        }
        Toast.makeText(getApplication(), "Store in favorite ${movie.title}", Toast.LENGTH_SHORT)
            .show()
        fetchFromRemote()

    }

    fun deleteFovorite(movie: Movie) {
        launch {
            MovieDatabase(getApplication()).movieDao().deleteMovie(movie)

        }
        Toast.makeText(getApplication(), "Delete favorite ${movie.title}", Toast.LENGTH_SHORT)
            .show()
        fetchFromRemote()
    }


    fun checkFavorite(movie: List<Movie>?) {
        if (movie != null) {
            for ( i in movie.indices) {
                movie[i].isFavorited = 0
                viewModelScope.launch {
                    val localMovie =
                        MovieDatabase(getApplication()).movieDao().getMovie(movie[i].id!!)
                    if(localMovie != null){
                        if (movie[i].id == localMovie.id) {
                            movie[i].isFavorited = 1
                        }
                    }

                }
            }
        }
        _movies.value = movie
    }


}
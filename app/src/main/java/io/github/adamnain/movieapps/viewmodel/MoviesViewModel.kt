package io.github.adamnain.movieapps.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.adamnain.movieapps.api.MoviesApiService
import io.github.adamnain.movieapps.db.MovieDatabase
import io.github.adamnain.movieapps.model.Movie
import io.github.adamnain.movieapps.model.MovieResponse
import io.github.adamnain.movieapps.view.MovieListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : BaseViewModel(application) {


    private val momvieService = MoviesApiService()
    private val disposable = CompositeDisposable()
    private lateinit var movieListener: MovieListener

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
                        _movies.value = movieResponse.results as List<Movie>?
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
        launch {
            val movies = MovieDatabase(getApplication()).movieDao().getAllMovies()
            _movies.value = movies
            _loading.value = false
            Toast.makeText(getApplication(), "Movies retrieved from database", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun setListener(listener: MovieListener) {
        this.movieListener = listener
    }



    fun storeFavorite(movie: Movie) {
        launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            val result = dao.insertMovie(movie)
            Toast.makeText(getApplication(), "Store in favorite $result", Toast.LENGTH_SHORT).show()

        }
    }

}
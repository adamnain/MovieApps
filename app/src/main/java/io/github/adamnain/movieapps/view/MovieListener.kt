package io.github.adamnain.movieapps.view

import io.github.adamnain.movieapps.model.Movie


interface MovieListener {
    fun onMovieClicked(movie: Movie)
}
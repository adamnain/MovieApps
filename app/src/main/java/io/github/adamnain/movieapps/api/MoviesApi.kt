package io.github.adamnain.movieapps.api

import io.github.adamnain.movieapps.model.MovieResponse
import io.github.adamnain.movieapps.util.GET_MOVIES
import io.reactivex.Single
import retrofit2.http.GET

interface MoviesApi {
    @GET(GET_MOVIES)
    fun getMovies(): Single<MovieResponse>
}
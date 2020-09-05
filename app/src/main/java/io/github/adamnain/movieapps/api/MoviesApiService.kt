package io.github.adamnain.movieapps.api

import io.github.adamnain.movieapps.model.MovieResponse
import io.github.adamnain.movieapps.util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MoviesApiService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MoviesApi::class.java)

    fun getMovies(): Single<MovieResponse> {
        return api.getMovies()
    }
}
package io.github.adamnain.movieapps.db

import androidx.room.*
import io.github.adamnain.movieapps.model.Movie

@Dao
interface MovieDao {
    @Insert
    suspend fun insertAll(vararg movie: Movie): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(vararg movie: Movie)

    @Delete
    fun deleteMovie(vararg movie: Movie)

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): Movie

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()

}
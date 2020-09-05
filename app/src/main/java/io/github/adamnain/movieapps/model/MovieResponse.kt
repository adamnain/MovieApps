package io.github.adamnain.movieapps.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieResponse(
	@SerializedName("page")
	val page: Int? = null,

	@SerializedName("totalPages")
	val totalPages: Int? = null,

	@SerializedName("results")
	val results: List<Movie?>? = null,

	@SerializedName("total_results")
	val totalResults: Int? = null
)

@Entity
data class Movie(
	@PrimaryKey(autoGenerate = false)
	@ColumnInfo(name = "id")
	@SerializedName("id")
	val id: Int? = null,

	@ColumnInfo(name = "overview")
	@SerializedName("overview")
	val overview: String? = null,

	@ColumnInfo(name = "original_language")
	@SerializedName("original_language")
	val originalLanguage: String? = null,

	@ColumnInfo(name = "original_title")
	@SerializedName("original_title")
	val originalTitle: String? = null,

	@ColumnInfo(name = "video")
	@SerializedName("video")
	val video: Boolean? = null,

	@ColumnInfo(name = "title")
	@SerializedName("title")
	val title: String? = null,

	@ColumnInfo(name = "genre_ids")
	@SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

	@ColumnInfo(name = "poster_path")
	@SerializedName("poster_path")
	val posterPath: String? = null,

	@ColumnInfo(name = "backdrop_path")
	@SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@ColumnInfo(name = "release_date")
	@SerializedName("release_date")
	val releaseDate: String? = null,

	@ColumnInfo(name = "popularity")
	@SerializedName("popularity")
	val popularity: Double? = null,

	@ColumnInfo(name = "vote_average")
	@SerializedName("vote_average")
	val voteAverage: Double? = null,

	@ColumnInfo(name = "adult")
	@SerializedName("adult")
	val adult: Boolean? = null,

	@ColumnInfo(name = "vote_count")
	@SerializedName("vote_count")
	val voteCount: Int? = null
)


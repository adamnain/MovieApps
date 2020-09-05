package io.github.adamnain.movieapps.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.adamnain.movieapps.model.Movie
import io.github.adamnain.movieapps.util.Converters

@Database(entities = arrayOf(Movie::class), version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase: RoomDatabase(){
    abstract fun movieDao(): MovieDao

    companion object{
        @Volatile private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "favorite_database"
        ).build()
    }


}
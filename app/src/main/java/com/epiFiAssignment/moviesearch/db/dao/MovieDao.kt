package com.epiFiAssignment.moviesearch.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epiFiAssignment.moviesearch.models.BookmarkMovieData
import com.epiFiAssignment.moviesearch.utils.Constants

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies : BookmarkMovieData)

    @Query("SELECT imdbID FROM ${Constants.MOVIE_TABLE_NAME}")
    suspend fun getMovieKeys() : List<String>

    @Query("DELETE FROM ${Constants.MOVIE_TABLE_NAME} WHERE imdbID = :movieId ")
    suspend fun deleteMovie(movieId : String)

    @Query("SELECT * FROM ${Constants.MOVIE_TABLE_NAME}")
    suspend fun getBookmarkedMovies() : List<BookmarkMovieData>
}
package com.epiFiAssignment.moviesearch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.epiFiAssignment.moviesearch.db.dao.MovieDao
import com.epiFiAssignment.moviesearch.models.BookmarkMovieData
import com.epiFiAssignment.moviesearch.utils.Constants

@Database(entities = [BookmarkMovieData::class], version = Constants.DB_VERSION)
abstract class MovieSearchDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}
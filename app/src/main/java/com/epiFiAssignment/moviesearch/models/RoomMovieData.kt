package com.epiFiAssignment.moviesearch.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.epiFiAssignment.moviesearch.utils.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.MOVIE_TABLE_NAME)
data class RoomMovieData(
    @PrimaryKey(autoGenerate = true)
    var movieId : Int,
    var imdbID        : String?            = null,
    var Title         : String?            = null,
    var Type          : String?            = null,
)

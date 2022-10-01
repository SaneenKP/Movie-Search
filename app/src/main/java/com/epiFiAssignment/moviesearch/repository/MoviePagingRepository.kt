package com.epiFiAssignment.moviesearch.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.epiFiAssignment.moviesearch.Constants
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.paging.MoviePagingSource
import retrofit2.http.Query
import javax.inject.Inject

class MoviePagingRepository @Inject constructor(
    private val movieRepository: MovieRepository
    ) {

    private var pagingConfig : PagingConfig

    init {
        pagingConfig = PagingConfig(pageSize = Constants.PAGE_SIZE , maxSize = Constants.MAX_PAGE_COUNT)
    }

    fun getMovies(searchQuery: String, movieType : String, )
    :LiveData<PagingData<Movie>> {
        return Pager(config = pagingConfig , pagingSourceFactory = {MoviePagingSource(searchQuery , movieType , movieRepository)}).liveData
    }
}
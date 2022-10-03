package com.epiFiAssignment.moviesearch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.epiFiAssignment.moviesearch.utils.Constants
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.repository.MovieRepository
import com.epiFiAssignment.moviesearch.utils.Constants.Companion.Status
import java.io.IOException

/**
    paging source which loads the data for pagination.
 */
class MoviePagingSource(
    private val searchQuery: String,
    private val searchType : String,
    private val movieRepository: MovieRepository
) : PagingSource<Int , Movie>() {

    private val INITIAL_PAGE = Constants.INITIAL_PAGE_NUMBER

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: INITIAL_PAGE
            val response = movieRepository.searchMovie(searchQuery , position , searchType)
            val endOfPaginationReached = (response.data?.result == null)
            if (response.status == Status.ERROR){
                throw Exception(response.message)
            }else{
                LoadResult.Page(
                    data = response.data!!.result,
                    prevKey = if(position == INITIAL_PAGE) null else position.minus(1),
                    nextKey = if (endOfPaginationReached) null else position.plus(1)
                )
            }
        }catch (e : Exception){
            LoadResult.Error(e)
        }catch (ioException : IOException){
            LoadResult.Error(ioException)
        }
    }
}
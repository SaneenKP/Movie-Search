package com.epiFiAssignment.moviesearch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.repository.MoviePagingRepository
import com.epiFiAssignment.moviesearch.repository.MovieRepository
import com.epiFiAssignment.moviesearch.retrofit.MovieRetrofitService
import com.epiFiAssignment.moviesearch.retrofit.ResponseWrapper
import com.epiFiAssignment.moviesearch.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviePagingRepository: MoviePagingRepository,
    private val movieRepository: MovieRepository
) : ViewModel(){

    var movieList : LiveData<PagingData<Movie>> = MutableLiveData()
    private val movieSelected : MutableLiveData<String> = MutableLiveData()
    private val movieType : MutableLiveData<String> = MutableLiveData()
    private val bookMarked : MutableLiveData<String> = MutableLiveData()
    val movieSearchResponse : MutableLiveData<ResponseWrapper<SearchResult?>> = MutableLiveData()

    fun getMovieType() : MutableLiveData<String>{
        return this.movieType
    }

    fun getMovieBookmarked() : MutableLiveData<String> {
       return this.bookMarked
    }

    fun getMovieSelected() : MutableLiveData<String> {
        return this.movieSelected
    }

    fun searchMovie(searchQuery : String , movieType : String) {

        movieList = moviePagingRepository.getMovies(searchQuery , movieType)

//        viewModelScope.launch {
//            movieSearchResponse.postValue(ResponseWrapper.loading())
//            val response = movieRepository.searchMovie(searchQuery , page , movieType)
//            movieSearchResponse.postValue(response)
//        }
    }
}
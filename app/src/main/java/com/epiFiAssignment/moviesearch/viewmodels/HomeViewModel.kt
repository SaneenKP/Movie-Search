package com.epiFiAssignment.moviesearch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.repository.MovieRepository
import com.epiFiAssignment.moviesearch.retrofit.MovieRetrofitService
import com.epiFiAssignment.moviesearch.retrofit.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){

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

    fun searchMovie(searchQuery : String , page : Int , movieType : String) {
        viewModelScope.launch {
            movieSearchResponse.postValue(ResponseWrapper.loading())
            val response = movieRepository.searchMovie(searchQuery , page , movieType)
            movieSearchResponse.postValue(response)
        }
    }
}
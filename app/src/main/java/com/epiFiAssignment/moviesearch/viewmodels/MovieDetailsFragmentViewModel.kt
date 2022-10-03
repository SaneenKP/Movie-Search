package com.epiFiAssignment.moviesearch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.repository.MovieRepository
import com.epiFiAssignment.moviesearch.retrofit.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the MovieDetails Bottom Sheet Fragment.
 * Triggers when response is receiver from api.
 */
@HiltViewModel
class MovieDetailsFragmentViewModel @Inject constructor(
    private var movieRepository: MovieRepository
) : ViewModel() {
    val movieResponse : MutableLiveData<ResponseWrapper<Movie?>> = MutableLiveData()

    fun getMovie(movieId : String){
        viewModelScope.launch {
            movieResponse.postValue(ResponseWrapper.loading())
            val response = movieRepository.getMovie(movieId)
            movieResponse.postValue(response)
        }
    }
}
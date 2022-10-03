package com.epiFiAssignment.moviesearch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epiFiAssignment.moviesearch.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Shared view model used only for the purpose of sharing data between activity and fragment.
 * Triggers when activity sends the movie Id to the fragment.
 */
@HiltViewModel
class SharedViewModel @Inject constructor(
    private var movieRepository: MovieRepository
) :ViewModel() {
    private val movieId : MutableLiveData<String> = MutableLiveData()

    fun getMovieId() : MutableLiveData<String>{
        return this.movieId
    }

}
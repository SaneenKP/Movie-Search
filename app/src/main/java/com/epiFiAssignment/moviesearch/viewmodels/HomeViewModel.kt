package com.epiFiAssignment.moviesearch.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.retrofit.MovieRetrofitClient
import com.epiFiAssignment.moviesearch.retrofit.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRetrofitClient: MovieRetrofitClient
) : ViewModel(){

    val movieResultResponse : MutableLiveData<ResponseWrapper<SearchResult>> = MutableLiveData()

    fun searchMovie(searchQuery : String , page : Int , movieType : String) {
        viewModelScope.launch {
            val response = movieRetrofitClient.searchMovie(searchQuery , page , movieType)
            movieResultResponse.postValue(ResponseWrapper.success(response))
        }
    }
}
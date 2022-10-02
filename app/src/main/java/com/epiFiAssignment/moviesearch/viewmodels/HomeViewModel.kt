package com.epiFiAssignment.moviesearch.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.epiFiAssignment.moviesearch.Constants
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.repository.MoviePagingRepository
import com.epiFiAssignment.moviesearch.repository.MovieRepository
import com.epiFiAssignment.moviesearch.retrofit.MovieRetrofitService
import com.epiFiAssignment.moviesearch.retrofit.ResponseWrapper
import com.epiFiAssignment.moviesearch.utils.MergedMovieTypeAndQueryStringLiveData
import com.epiFiAssignment.moviesearch.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviePagingRepository: MoviePagingRepository,
) : ViewModel(){

    private val changeMovieType : MutableLiveData<String> = MutableLiveData()
    private val movieSelected : MutableLiveData<String> = MutableLiveData()
    private val bookMarked : MutableLiveData<String> = MutableLiveData()
    private val movieType : MutableLiveData<String> = MutableLiveData()
    private val searchQuery : MutableLiveData<String> = MutableLiveData()

    val movieList = Transformations.switchMap(MergedMovieTypeAndQueryStringLiveData(searchQuery , movieType)){
        it?.second?.let { it1 -> it.first?.let { it2 -> moviePagingRepository.getMovies(searchQuery = it2, movieType = it1) } }
    }

    fun getMovieBookmarked() : MutableLiveData<String> {
       return this.bookMarked
    }

    fun getMovieSelected() : MutableLiveData<String> {
        return this.movieSelected
    }

    fun changeMovieType(movieType : String){
        this.changeMovieType.value = movieType
    }
    fun getMovieTypeChangeStatus() : MutableLiveData<String>{
        return this.changeMovieType
    }

    private fun setMovieType(movieType : String){
        this.movieType.value = movieType
    }

    private fun setSearchQuery(searchQuery : String){
        this.searchQuery.value = searchQuery
    }

    fun searchMovie(searchQuery: String , movieType: String){
        setSearchQuery(searchQuery = searchQuery)
        setMovieType(movieType = movieType)
    }
}
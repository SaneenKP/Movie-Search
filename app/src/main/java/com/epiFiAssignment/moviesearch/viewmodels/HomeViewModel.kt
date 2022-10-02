package com.epiFiAssignment.moviesearch.viewmodels

import androidx.lifecycle.*
import com.epiFiAssignment.moviesearch.repository.MoviePagingRepository
import com.epiFiAssignment.moviesearch.utils.MergedMovieTypeAndQueryStringLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviePagingRepository: MoviePagingRepository,
) : ViewModel(){

    private val movieTypeClickState = mutableListOf<Boolean>(true , false , false , false)
    private val changeMovieType : MutableLiveData<String> = MutableLiveData()
    private val movieSelected : MutableLiveData<String> = MutableLiveData()
    private val bookMarked : MutableLiveData<String> = MutableLiveData()
    private val movieType : MutableLiveData<String> = MutableLiveData()
    private val searchQuery : MutableLiveData<String> = MutableLiveData()

    val movieList = Transformations.switchMap(MergedMovieTypeAndQueryStringLiveData(searchQuery , movieType)){
        it?.second?.let { it1 -> it.first?.let { it2 -> moviePagingRepository.getMovies(searchQuery = it2, movieType = it1) } }
    }

    fun getClickState() : MutableList<Boolean>{
        return this.movieTypeClickState
    }

    fun setClickState(pos : Int , status : Boolean){
        this.movieTypeClickState[pos] = status
        movieTypeClickState.forEachIndexed { index: Int, b: Boolean ->
            if (index != pos) movieTypeClickState[index] = false
        }
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
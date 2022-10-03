package com.epiFiAssignment.moviesearch.viewmodels

import androidx.lifecycle.*
import com.epiFiAssignment.moviesearch.db.MovieSearchDatabase
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.models.RoomMovieData
import com.epiFiAssignment.moviesearch.repository.MoviePagingRepository
import com.epiFiAssignment.moviesearch.utils.MergedMovieTypeAndQueryStringLiveData
import com.epiFiAssignment.moviesearch.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The main view model used by the home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviePagingRepository: MoviePagingRepository,
    private val movieSearchDatabase: MovieSearchDatabase
) : ViewModel(){

    //Holds the state of the movieType buttons ie if clicked or not
    private val movieTypeClickState = mutableListOf<Boolean>(true , false , false , false)

    //Triggers when movie type gets changed. Sends the changed movie type.
    private val changeMovieType : MutableLiveData<String> = MutableLiveData()

    //Triggers when a movie is selected.
    private val movieSelected : MutableLiveData<String> = MutableLiveData()

    //Triggers when a movie is bookmarked.
    private val bookMarked : MutableLiveData<Movie> = MutableLiveData()

    //Holds the movie type data.
    private val movieType : MutableLiveData<String> = MutableLiveData()

    //Triggers when user searches something.
    private val searchQuery : MutableLiveData<String> = MutableLiveData()

    //This is triggered when any of the two searchQuery or MovieType is triggered.
    //The mediatorLiveData class merges the two data.
    val movieList = Transformations.switchMap(MergedMovieTypeAndQueryStringLiveData(searchQuery , movieType)){
        it?.second?.let { it1 -> it.first?.let { it2 -> moviePagingRepository.getMovies(searchQuery = it2, movieType = it1) } }
    }

    fun getClickState() : MutableList<Boolean>{
        return this.movieTypeClickState
    }

    //changes the click state of all the other movie type buttons to false.
    fun setClickState(pos : Int , status : Boolean){
        this.movieTypeClickState[pos] = status
        movieTypeClickState.forEachIndexed { index: Int, b: Boolean ->
            if (index != pos) movieTypeClickState[index] = false
        }
    }

    fun getMovieBookmarked() : MutableLiveData<Movie> {
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

    fun addMovieToDatabase(movie: Movie){
        viewModelScope.launch {
            movieSearchDatabase.movieDao().addMovies(
                RoomMovieData(0, movie.imdbID, movie.Title, movie.Type)
            )
        }
    }

    fun removeMovieFromDatabase(movieId : String){
        viewModelScope.launch {
            movieSearchDatabase.movieDao().deleteMovie(movieId)
        }
    }
}
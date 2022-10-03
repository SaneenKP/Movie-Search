package com.epiFiAssignment.moviesearch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epiFiAssignment.moviesearch.db.MovieSearchDatabase
import com.epiFiAssignment.moviesearch.models.BookmarkMovieData
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.repository.MoviePagingRepository
import com.epiFiAssignment.moviesearch.utils.MergedMovieTypeAndQueryStringLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    var movieDatabase : MovieSearchDatabase
) : ViewModel(){
    var bookmarksList : MutableLiveData<List<BookmarkMovieData>> = MutableLiveData()

    fun getBookMarkList(){
        viewModelScope.launch {
            bookmarksList.value = movieDatabase.movieDao().getBookmarkedMovies()
        }
    }
}

package com.epiFiAssignment.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import com.epiFiAssignment.moviesearch.Constants.Companion.Status
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.Loader
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.epiFiAssignment.moviesearch.adapters.LoaderAdapter
import com.epiFiAssignment.moviesearch.adapters.MovieAdapter
import com.epiFiAssignment.moviesearch.adapters.MovieTypeAdapter
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.utils.Utils
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel
import com.epiFiAssignment.moviesearch.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class Home : AppCompatActivity() , SearchView.OnQueryTextListener{

    lateinit var movieViewModel : HomeViewModel
    lateinit var movieAdapter: MovieAdapter
    lateinit var movieTypeAdapter: MovieTypeAdapter
    private var currentMovieType = Constants.INITIAL_MOVIE_TYPE
    private var currentSearchQuery = Constants.INITIAL_MOVIE_SEARCH_QUERY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
    }


    private fun init(){

        movieViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        movieTypeAdapter = MovieTypeAdapter( movieViewModel)
        movieAdapter = MovieAdapter(movieViewModel)

        movies_recyclerview.apply {
            layoutManager = GridLayoutManager(context , Constants.COLUMN_COUNT)
            setHasFixedSize(true)
            adapter = movieAdapter.withLoadStateFooter(
                footer = LoaderAdapter()
            )
        }

        movie_types_recyclerview.apply {
            layoutManager = LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , false)
            setHasFixedSize(true)
            adapter = movieTypeAdapter
        }

        searchMovie(currentSearchQuery, currentMovieType)

        movie_search.setOnQueryTextListener(this)

        observeViewModels()

    }

    private fun setUpSearchView(){

    }

    private fun searchMovie(searchQuery : String , movieType : String){
        movieViewModel.searchMovie(searchQuery ,  movieType)
    }

    private fun observeViewModels(){

        movieViewModel.movieList.observe(this) {
            movieAdapter.submitData(lifecycle, it)
        }

        movieViewModel.getMovieType().observe(this) { movieType ->
            searchMovie(currentSearchQuery,  currentMovieType)
        }

        movieViewModel.getMovieBookmarked().observe(this) { movieId ->
            handleMovieBookMarked(movieId)
        }

        movieViewModel.getMovieSelected().observe(this) { movieId ->
            handleOnMovieClicked(movieId)
        }

    }

    private fun handleOnMovieClicked(movieId: String) {
        var movieDetailsFragment : MovieDetailsFragment = MovieDetailsFragment()
        movieDetailsFragment.show(supportFragmentManager , "show")

        var fragmentViewModel : SharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        fragmentViewModel.getMovieId().value = movieId
    }

    private fun handleMovieBookMarked(movieId : String){
        Utils.toast(this , "Bookmarked")
    }

    private fun handleErrorState(){

    }

    private fun handleLoadingState(){
    }

    private fun handleNetworkError(){

    }

    private fun handleNoDataState(){

    }

    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
        Utils.toast(this , "${searchQuery}")
        if (searchQuery != null) {
            this.currentSearchQuery = searchQuery
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

}
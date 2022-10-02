package com.epiFiAssignment.moviesearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.epiFiAssignment.moviesearch.adapters.LoaderAdapter
import com.epiFiAssignment.moviesearch.adapters.MovieAdapter
import com.epiFiAssignment.moviesearch.adapters.MovieTypeAdapter
import com.epiFiAssignment.moviesearch.databinding.ActivityHomeBinding
import com.epiFiAssignment.moviesearch.utils.Utils
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel
import com.epiFiAssignment.moviesearch.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class Home : AppCompatActivity() ,
    SearchView.OnQueryTextListener ,
    SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener{

    lateinit var movieViewModel : HomeViewModel
    lateinit var movieAdapter: MovieAdapter
    lateinit var movieTypeAdapter: MovieTypeAdapter
    private var currentMovieType = Constants.INITIAL_MOVIE_TYPE
    private var currentSearchQuery = Constants.DEFAULT_MOVIE_SEARCH_QUERY
    lateinit var homeViewBinding : ActivityHomeBinding
    private val FRAGMENT_TAG = "movie_details_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewBinding = DataBindingUtil.setContentView(this , R.layout.activity_home)

        init()
    }


    private fun init(){

        swipeRefresh.setOnRefreshListener(this)
        movieViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        homeViewBinding.titleTv.setOnClickListener(this)
        homeViewBinding.showBookmarksBtn.setOnClickListener(this)

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

        movie_search.setOnQueryTextListener(this)
        searchMovie(currentSearchQuery , currentMovieType)

        observeViewModels()

    }

    private fun searchMovie(searchQuery : String , movieType : String){
        movieViewModel.searchMovie(searchQuery , movieType)
    }


    private fun observeViewModels(){

        movieViewModel.movieList.observe(this) {
            if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
            movieAdapter.submitData(lifecycle, it)
        }

        movieViewModel.getMovieTypeChangeStatus().observe(this) { movieType ->
            handleMovieTypeChange(movieType)
        }

        movieViewModel.getMovieBookmarked().observe(this) { movieId ->
            handleMovieBookMarked(movieId)
        }

        movieViewModel.getMovieSelected().observe(this) { movieId ->
            handleOnMovieClicked(movieId)
        }

    }

    private fun handleMovieTypeChange(movieType: String) {
        this.currentMovieType = movieType
        searchMovie(currentSearchQuery , currentMovieType)
    }

    private fun handleOnMovieClicked(movieId: String) {
        var movieDetailsFragment : MovieDetailsFragment = MovieDetailsFragment()
        movieDetailsFragment.show(supportFragmentManager , FRAGMENT_TAG)

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
            movieViewModel.searchMovie(currentSearchQuery , currentMovieType)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    override fun onRefresh() {
        movieAdapter.refresh()
    }

    override fun onClick(view: View?) {

        when(view?.id) {
           homeViewBinding.titleTv.id -> {
                homeViewBinding.moviesRecyclerview.smoothScrollToPosition(0)
           }
            R.id.show_bookmarks_btn -> {
                var intent = Intent(applicationContext , Bookmarks::class.java)
                startActivity(intent)
           }
        }
    }

}
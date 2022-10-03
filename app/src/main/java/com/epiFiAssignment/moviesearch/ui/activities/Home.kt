package com.epiFiAssignment.moviesearch.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.epiFiAssignment.moviesearch.utils.Constants
import com.epiFiAssignment.moviesearch.R
import com.epiFiAssignment.moviesearch.adapters.LoaderAdapter
import com.epiFiAssignment.moviesearch.adapters.MovieAdapter
import com.epiFiAssignment.moviesearch.adapters.MovieTypeAdapter
import com.epiFiAssignment.moviesearch.databinding.ActivityHomeBinding
import com.epiFiAssignment.moviesearch.ui.fragments.MovieDetailsFragment
import com.epiFiAssignment.moviesearch.utils.Utils
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel
import com.epiFiAssignment.moviesearch.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.HttpException
import java.io.IOException

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

        //initialise view models.
        movieViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        //set listeners.
        homeViewBinding.titleTv.setOnClickListener(this)
        homeViewBinding.showBookmarksBtn.setOnClickListener(this)
        homeViewBinding.movieSearch.setOnQueryTextListener(this)

        //Initialise Adapters.
        movieTypeAdapter = MovieTypeAdapter( movieViewModel)
        movieAdapter = MovieAdapter(movieViewModel)
        handleLoadStateError()

        //Initialise RecyclerViews.
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

        //Initial search for the movies with the default searchQuery and Default MovieType
        searchMovie(currentSearchQuery , currentMovieType)

        //Observe ViewModels.
        observeViewModels()

    }

    //method which handles the error of paging and api calls.
    private fun handleLoadStateError(){
        movieAdapter.addLoadStateListener {loadState ->
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            when (val throwable = errorState?.error) {
                is IOException -> {
                    handleSomethingWentWrong()
                }
                is HttpException -> {
                    if (throwable.code() == 401) {
                        handleSomethingWentWrong()
                    }
                    else {
                        handleSomethingWentWrong()
                    }
                }
                else ->{
                    if (throwable != null) {
                        when(throwable.message){
                            Constants.MOVIE_NOT_FOUND ->  handleNoDataState()
                            Constants.SOMETHING_WENT_WRONG_ERROR -> handleSomethingWentWrong()
                        }
                    }
                }
            }
        }
    }

    //Searches movies by making api call through view model.
    //Before that it removes all the Utility views which was displayed due to some error.
    private fun searchMovie(searchQuery : String , movieType : String){
        removeVisibilityOfAllUtilityView()
        homeViewBinding.moviesRecyclerview.visibility = View.VISIBLE
        movieViewModel.searchMovie(searchQuery , movieType)
    }

    //all view model Observers
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

    //searches movie when movie type changes with the respective movie type.
    private fun handleMovieTypeChange(movieType: String) {
        this.currentMovieType = movieType
        searchMovie(currentSearchQuery , currentMovieType)
    }

    //Opens the movie details fragment when a movie is clicked.
    private fun handleOnMovieClicked(movieId: String) {
        var movieDetailsFragment : MovieDetailsFragment = MovieDetailsFragment()
        movieDetailsFragment.show(supportFragmentManager , FRAGMENT_TAG)

        var fragmentViewModel : SharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        fragmentViewModel.getMovieId().value = movieId
    }

    //handles when a movie is bookmarked.
    private fun handleMovieBookMarked(movieId : String){
        Utils.toast(this , "Bookmarked")
    }

    private fun handleLoadingState(){
    }

    private fun handleNetworkError(){
    }

    //methods which handles when something went wrong condition triggers.
    private fun handleSomethingWentWrong(){
       homeViewBinding.moviesRecyclerview.visibility = View.GONE
        something_went_wrong_view.visibility = View.VISIBLE
    }

    //method which handles when no data is found.
    private fun handleNoDataState(){
        homeViewBinding.moviesRecyclerview.visibility = View.GONE
        no_data_available_view.visibility = View.VISIBLE
    }

    //Removes visibility of all utility views.
    private fun removeVisibilityOfAllUtilityView(){
        no_data_available_view.visibility = View.GONE
        something_went_wrong_view.visibility = View.GONE
    }

    //function when a search query is submitted.
    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
        Utils.toast(this , "${searchQuery}")
        if (searchQuery != null) {
            this.currentSearchQuery = searchQuery
            searchMovie(currentSearchQuery , currentMovieType)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    //during swipe refresh
    override fun onRefresh() {
        movieAdapter.refresh()
    }

    override fun onClick(view: View?) {

        when(view?.id) {
            //on clicking the app name , the recyclerview will scroll to the top.
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
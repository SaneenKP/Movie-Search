package com.epiFiAssignment.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.epiFiAssignment.moviesearch.Constants.Companion.Status
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epiFiAssignment.moviesearch.adapters.MovieAdapter
import com.epiFiAssignment.moviesearch.adapters.MovieTypeAdapter
import com.epiFiAssignment.moviesearch.listeners.MovieTypeClickListener
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.utils.Utils
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class Home : AppCompatActivity() , MovieTypeClickListener {

    lateinit var movieViewModel : HomeViewModel
    private var movieAdapter: MovieAdapter? = null
    lateinit var movieTypeAdapter: MovieTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
    }


    private fun init(){

        movieTypeAdapter = MovieTypeAdapter( this)
        movieViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        movies_recyclerview.apply {
            layoutManager = GridLayoutManager(context , Constants.COLUMN_COUNT)
            setHasFixedSize(true)
        }

        movie_types_recyclerview.apply {
            layoutManager = LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , false)
            setHasFixedSize(true)
            adapter = movieTypeAdapter
        }

        searchMovie(Constants.INITIAL_MOVIE_SEARCH_QUERY, Constants.INITIAL_PAGE_NUMBER , Constants.INITIAL_MOVIE_TYPE)

        observeViewModels()

    }

    private fun searchMovie(searchQuery : String , pageNo : Int , movieType : String){
        movieViewModel.searchMovie(searchQuery , pageNo , movieType)
    }

    private fun observeViewModels(){
        movieViewModel.movieSearchResponse.observe(this) { it ->
            it?.let { response ->
                when (response.status) {
                    Status.SUCCESS -> handleSuccessState(response.data)
                    Status.ERROR -> handleErrorState()
                    Status.LOADING -> handleLoadingState()
                    Status.NETWORK_ERROR -> handleNetworkError()
                }
            }
        }



    }

    private fun handleSuccessState(data: SearchResult?) {

        if (data != null){
            if (data.result != null){

                if (movieAdapter==null){
                    movieAdapter = MovieAdapter(data.result)
                    movies_recyclerview.adapter = movieAdapter
                }else{
                    movieAdapter!!.updateData(data.result)
                }
            }
            else{
                handleNoDataState()
            }
        }else{
            handleNoDataState()
        }
    }

    private fun handleErrorState(){

    }

    private fun handleLoadingState(){
    }

    private fun handleNetworkError(){

    }


    private fun handleNoDataState(){

    }

    override fun onClickMovieType(movieType: String) {
        searchMovie("batman" , 1 , movieType)
    }

}
package com.epiFiAssignment.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.epiFiAssignment.moviesearch.Constants.Companion.Status
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.epiFiAssignment.moviesearch.adapters.MovieAdapter
import com.epiFiAssignment.moviesearch.adapters.MovieTypeAdapter
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.utils.Utils
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class Home : AppCompatActivity() {

    lateinit var movieViewModel : HomeViewModel
    lateinit var movieAdapter: MovieAdapter
    lateinit var movieTypeAdapter: MovieTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
    }


    private fun init(){

        movieTypeAdapter = MovieTypeAdapter()
        movieViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        movieViewModel.searchMovie("batman" , 1 , "movie")

        movies_recyclerview.apply {
            layoutManager = GridLayoutManager(context , Constants.COLUMN_COUNT)
            setHasFixedSize(true)
        }

        movie_types_recyclerview.apply {
            layoutManager = LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , false)
            setHasFixedSize(true)
            adapter = movieTypeAdapter
        }

        observeViewModels()

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

        Utils.toast(context = this , "success")
        if (data != null){
            if (data.result != null){
                movieAdapter = MovieAdapter(data.result)
                movies_recyclerview.adapter = movieAdapter
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
        Utils.toast(context = this , "loading")
    }

    private fun handleNetworkError(){

    }


    private fun handleNoDataState(){

    }

}
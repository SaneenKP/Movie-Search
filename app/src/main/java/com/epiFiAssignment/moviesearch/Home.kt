package com.epiFiAssignment.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.epiFiAssignment.moviesearch.Constants.Companion.Status
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.epiFiAssignment.moviesearch.adapters.MovieAdapter
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class Home : AppCompatActivity() {

    lateinit var movieViewModel : HomeViewModel
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
    }


    private fun init(){

        movieViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        movieViewModel.searchMovie("batman" , 1 , "movie")

        movies_recyclerview.apply {
            layoutManager = GridLayoutManager(context , Constants.COLUMN_COUNT)
            setHasFixedSize(true)
        }

        observeViewModels()

    }

    private fun observeViewModels(){
        movieViewModel.movieResultResponse.observe(this) { it ->
            it?.let { response ->
                when (response.status) {
                    Status.SUCCESS -> handleSuccessState(response.data)
                    Status.ERROR -> handleErrorState()
                    Status.LOADING -> handleLoadingState()
                }
            }
        }



    }
    private fun handleSuccessState(data: SearchResult?) {

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
    }


    private fun handleNoDataState(){

    }

}
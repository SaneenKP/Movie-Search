package com.epiFiAssignment.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : AppCompatActivity() {

    lateinit var movieViewModel : HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        movieViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        movieViewModel.searchMovie("batman" , 1 , "movie")
    }
}
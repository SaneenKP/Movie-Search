package com.epiFiAssignment.moviesearch

import android.app.Dialog
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.epiFiAssignment.moviesearch.utils.Utils
import com.epiFiAssignment.moviesearch.viewmodels.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.epiFiAssignment.moviesearch.Constants.Companion.Status
import com.epiFiAssignment.moviesearch.databinding.MovieDetailsBottomSheetBinding
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.models.Ratings


class MovieDetailsFragment : BottomSheetDialogFragment() {

    lateinit var fragmentViewModel: SharedViewModel
    private var movieId : String = ""
    private var movie : Movie? = null
    lateinit var movieDetailsDetailsBottomSheetBinding : MovieDetailsBottomSheetBinding

    override fun setupDialog(dialog: Dialog, style: Int) {
        movieDetailsDetailsBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(context) , R.layout.movie_details_bottom_sheet , null , false)
        dialog.setContentView(movieDetailsDetailsBottomSheetBinding.root)

        init()
    }

    private fun init(){
        fragmentViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel(){

        activity?.let {
            fragmentViewModel.getMovieId().observe(it) { movieId ->
                this.movieId = movieId
                fragmentViewModel.getMovie(movieId)
            }
        }

        activity?.let {
            fragmentViewModel.movieResponse.observe(it) { response ->

                when (response.status) {
                    Status.SUCCESS -> {
                        if (response!=null){
                            if (response.data!=null){
                                bindData(response.data)
                            }
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                    Status.NETWORK_ERROR -> {
                    }
                }
            }
        }

    }

    private fun bindData(data: Movie) {
        this.movie = data

        configureRatings(data.Ratings)
        configureData(data)

        movieDetailsDetailsBottomSheetBinding.movie = this.movie

    }

    private fun configureData(data: Movie) {
        this.movie?.averageRating = data.imdbRating?.toDouble()
            ?.let { Utils.calculateRating(it).toInt() }
    }

    private fun configureRatings(ratings: ArrayList<Ratings>) {

        for (rating in ratings){
            when(rating.Source){
                Constants.IMBD_RATING -> this.movie?.imbdRatingValue = rating.Value
                Constants.ROTTEN_TOMATO_RATING -> this.movie?.rtRating = rating.Value
                Constants.METACRITIC_RATING -> this.movie?.mtRating = rating.Value
            }
        }
    }

}
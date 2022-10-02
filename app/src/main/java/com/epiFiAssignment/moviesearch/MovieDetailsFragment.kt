package com.epiFiAssignment.moviesearch

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.epiFiAssignment.moviesearch.Constants.Companion.Status
import com.epiFiAssignment.moviesearch.databinding.MovieDetailsBottomSheetBinding
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.models.Ratings
import com.epiFiAssignment.moviesearch.utils.Utils
import com.epiFiAssignment.moviesearch.viewmodels.MovieDetailsFragmentViewModel
import com.epiFiAssignment.moviesearch.viewmodels.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.movie_details_bottom_sheet.*
import kotlinx.android.synthetic.main.somthing_went_wrong_view.*


class MovieDetailsFragment() : BottomSheetDialogFragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var fragmentViewModel : MovieDetailsFragmentViewModel

    private var movieId : String = ""
    private var movie : Movie? = null
    lateinit var movieDetailsDetailsBottomSheetBinding : MovieDetailsBottomSheetBinding
    private var fragmentActivity : Activity? = null

    override fun setupDialog(dialog: Dialog, style: Int) {
        movieDetailsDetailsBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(context) , R.layout.movie_details_bottom_sheet , null , false)
        dialog.setContentView(movieDetailsDetailsBottomSheetBinding.root)

        init()
    }

    private fun init(){
        if (fragmentActivity==null) fragmentActivity = activity
        setBottomSheetHeight()
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        fragmentViewModel = ViewModelProvider(requireActivity())[MovieDetailsFragmentViewModel::class.java]

        observeViewModel()


        movieDetailsDetailsBottomSheetBinding.closeBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        movieDetailsDetailsBottomSheetBinding.movie = null
    }

    private fun setBottomSheetHeight(){
        var nestedScrollView = movieDetailsDetailsBottomSheetBinding.bottomSheetSv
        var layoutParams = nestedScrollView.layoutParams
         layoutParams.height = getBottomSheetMaxHeight()
    }

    private fun getBottomSheetMaxHeight() : Int{
        return (getScreenHeight()*90)/100
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    private fun observeViewModel(){

        activity?.let {
            sharedViewModel.getMovieId().observe(it) { movieId ->
                this.movieId = movieId
                fragmentViewModel.getMovie(movieId)
            }
        }

        activity?.let {
            fragmentViewModel.movieResponse.observe(it) { response ->

                when (response.status) {
                    Status.SUCCESS -> {
                        handleSuccess()
                        if (response!=null){
                            if (response.data!=null){
                                bindData(response.data)
                            }
                        }
                    }
                    Status.LOADING -> {
                        handleLoading()
                    }
                    Status.ERROR -> {
                        handleError()
                    }
                    Status.NETWORK_ERROR -> {
                        handleError()
                    }
                }
            }
        }

    }

    private fun handleSuccess(){
        changeLoadingView(View.GONE)
        changeSomethingWentWrongView(View.GONE)
        changeBottomSheetContentView(View.VISIBLE)
    }

    private fun handleError(){
        changeLoadingView(View.GONE)
        changeBottomSheetContentView(View.GONE)
        changeSomethingWentWrongView(View.VISIBLE)
    }

    private fun handleLoading(){
        changeBottomSheetContentView(View.GONE)
        changeSomethingWentWrongView(View.GONE)
        changeLoadingView(View.VISIBLE)
    }

    private fun changeLoadingView(status: Int) {
        movieDetailsDetailsBottomSheetBinding.loadingView.root.layoutParams.height = getBottomSheetMaxHeight()
        movieDetailsDetailsBottomSheetBinding.loadingView.root.visibility = status
    }

    private fun changeSomethingWentWrongView(status : Int){
        movieDetailsDetailsBottomSheetBinding.somethingWentWrong.root.layoutParams.height = getBottomSheetMaxHeight()
        movieDetailsDetailsBottomSheetBinding.somethingWentWrong.root.visibility = status
    }

    private fun changeBottomSheetContentView(status: Int){
        movieDetailsDetailsBottomSheetBinding.entireBottomSheetContentContainer.visibility = status
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
                Constants.IMBD_RATING -> this.movie?.imbdRatingValue =   if(rating.Value.isNullOrBlank())  "N/A" else rating.Value
                Constants.ROTTEN_TOMATO_RATING -> this.movie?.rtRating = if(rating.Value.isNullOrBlank())  "N/A" else rating.Value
                Constants.METACRITIC_RATING -> this.movie?.mtRating =    if(rating.Value.isNullOrBlank())  "N/A" else rating.Value
            }
        }
    }
}
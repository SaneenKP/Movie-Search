package com.epiFiAssignment.moviesearch.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.epiFiAssignment.moviesearch.databinding.MovieViewBinding
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.utils.Utils
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel

class MovieAdapter(
    private var movieList: ArrayList<Movie>?,
    private var movieViewModel : HomeViewModel
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return MovieViewHolder(context = parent.context , movieDataBinding = binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (movieList!=null){
            holder.bindData(movieList!![position])
        }
    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }

    fun updateData(movieList: ArrayList<Movie>?){
        clearData()
        this.movieList = ArrayList(movieList)
        notifyDataSetChanged()
    }

    private fun clearData(){
        this.movieList?.clear()
    }

    inner class MovieViewHolder(
        private val context: Context,
        private val movieDataBinding : MovieViewBinding
    ) : RecyclerView.ViewHolder(movieDataBinding.root) , View.OnClickListener{

        private var movie : Movie? = null

        init {
            movieDataBinding.root.setOnClickListener(this)
            movieDataBinding.bookmark.setOnClickListener(this)
        }

        fun bindData(movie: Movie){
            this.movie = movie
            movieDataBinding.movie = movie
        }

        override fun onClick(p0: View?) {
            when(p0?.id){
                movieDataBinding.bookmark.id -> {
                    movieViewModel.getMovieBookmarked().value = this.movie?.imdbID
                }

                movieDataBinding.root.id -> {
                    movieViewModel.getMovieSelected().value = this.movie?.imdbID
                }
            }
        }
    }

}
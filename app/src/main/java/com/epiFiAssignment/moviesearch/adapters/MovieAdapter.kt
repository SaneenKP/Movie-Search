package com.epiFiAssignment.moviesearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.epiFiAssignment.moviesearch.R
import com.epiFiAssignment.moviesearch.databinding.MovieViewBinding
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel

/**
 * Adapter responsible for the home screen pagination of movies.
 */
class MovieAdapter(
    private val movieViewModel: HomeViewModel
): PagingDataAdapter<Movie , MovieAdapter.MovieViewHolder>(COMPARATOR){


    inner class MovieViewHolder(
        private val movieDataBinding : MovieViewBinding
    ) : RecyclerView.ViewHolder(movieDataBinding.root) , View.OnClickListener{

        private var movie : Movie? = null

        init {
            movieDataBinding.root.setOnClickListener(this)
            movieDataBinding.bookmark.setOnClickListener(this)
        }

        fun bindData(movie: Movie){
            this.movie = movie
            this.movie!!.bookmarkStatus = getBookmarkedOrNotStatus(movie)
            setBookMarkStatus(this.movie!!.bookmarkStatus)
            movieDataBinding.movie = movie
        }

        private fun getBookmarkedOrNotStatus(movie: Movie) : Boolean{
            return movieViewModel.checkBookmarkedMovieOrNot(movie.imdbID!!)
        }

        fun setBookMarkStatus(status : Boolean){
            if (status) movieDataBinding.bookmark.background = movieDataBinding.root.context.resources.getDrawable(R.drawable.bookmark_saved_icon)
            else movieDataBinding.bookmark.background = movieDataBinding.root.context.resources.getDrawable(R.drawable.bookmark_icon)
        }

        fun changeBookmarkStatus(){
            if (this.movie!!.bookmarkStatus){
               this.movie!!.bookmarkStatus = false
               movieDataBinding.bookmark.background = movieDataBinding.root.context.resources.getDrawable(R.drawable.bookmark_icon)
            }else{
                this.movie!!.bookmarkStatus = true
                movieDataBinding.bookmark.background = movieDataBinding.root.context.resources.getDrawable(R.drawable.bookmark_saved_icon)
            }
        }

        override fun onClick(p0: View?) {
            when(p0?.id){
                movieDataBinding.bookmark.id -> {
                    changeBookmarkStatus()
                    movieViewModel.getMovieBookmarked().value = this.movie
                }
                movieDataBinding.root.id -> {
                    movieViewModel.getMovieSelected().value = this.movie?.imdbID
                }
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>(){

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie!=null) holder.bindData(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return MovieViewHolder(movieDataBinding = binding)
    }
}
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

class MovieAdapter(
    private val movieList: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return MovieViewHolder(context = parent.context , movieDataBinding = binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        movieList[position].let { movie ->
            holder.bindData(movie)
        }
    }

    override fun getItemCount(): Int {
      return movieList.size
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
                    Utils.toast(context , "Bookmarked")
                }

                movieDataBinding.root.id -> {
                    Utils.toast(context , "Clicked")
                }
            }
        }
    }

}
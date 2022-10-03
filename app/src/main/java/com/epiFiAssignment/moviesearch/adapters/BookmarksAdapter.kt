package com.epiFiAssignment.moviesearch.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epiFiAssignment.moviesearch.R
import com.epiFiAssignment.moviesearch.databinding.ActivityBookmarksBinding
import com.epiFiAssignment.moviesearch.databinding.MovieViewBinding
import com.epiFiAssignment.moviesearch.models.BookmarkMovieData
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.ui.activities.Bookmarks
import com.epiFiAssignment.moviesearch.utils.Utils

/**
 * Adapter used to display movies in the book mark section.
 */
class BookmarksAdapter(
    var movieList : List<BookmarkMovieData>?
) : RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {


    inner class BookmarksViewHolder(
        context: Context,
        var movieViewBinding: MovieViewBinding
    ) : RecyclerView.ViewHolder(movieViewBinding.root){

        private var movie : Movie? = null
        private fun getMovieDataFromBookMarkData(data : BookmarkMovieData) : Movie{
            return Movie(
                data.imdbID,
                data.Title,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                data.Poster,
                arrayListOf(),
                null,
                null,
                null,
                data.Type,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true,
            )
        }
        fun bind(data : BookmarkMovieData){
            this.movie = getMovieDataFromBookMarkData(data)
            setBookMarkStatus(this.movie!!.bookmarkStatus)
            movieViewBinding.movie = this.movie
        }

        fun setBookMarkStatus(status : Boolean){
            if (status) movieViewBinding.bookmark.background = movieViewBinding.root.context.resources.getDrawable(
                R.drawable.bookmark_saved_icon)
            else movieViewBinding.bookmark.background = movieViewBinding.root.context.resources.getDrawable(
                R.drawable.bookmark_icon)
        }

        fun changeBookmarkStatus(){
            if (this.movie!!.bookmarkStatus){
                this.movie!!.bookmarkStatus = false
                movieViewBinding.bookmark.background = movieViewBinding.root.context.resources.getDrawable(
                    R.drawable.bookmark_icon)
            }else{
                this.movie!!.bookmarkStatus = true
                movieViewBinding.bookmark.background = movieViewBinding.root.context.resources.getDrawable(
                    R.drawable.bookmark_saved_icon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        val binding = MovieViewBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return BookmarksViewHolder(context = parent.context ,  movieViewBinding = binding)
    }

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        if (movieList!=null) holder.bind(movieList!![position])
    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }
}
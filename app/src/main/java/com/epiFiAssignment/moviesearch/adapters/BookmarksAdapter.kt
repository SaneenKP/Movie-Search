package com.epiFiAssignment.moviesearch.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epiFiAssignment.moviesearch.databinding.ActivityBookmarksBinding
import com.epiFiAssignment.moviesearch.models.Movie

class BookmarksAdapter(
    var movieList : ArrayList<Movie>
) : RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {


    inner class BookmarksViewHolder(
        context: Context,
        binding: ActivityBookmarksBinding
    ) : RecyclerView.ViewHolder(binding.root){

        private var movie : Movie? = null
        init {

        }
        fun bind(data : Movie){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        val binding = ActivityBookmarksBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return BookmarksViewHolder(context = parent.context ,  binding = binding)
    }

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        if (movieList!=null) holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return if (movieList!=null) movieList.size else 0
    }
}
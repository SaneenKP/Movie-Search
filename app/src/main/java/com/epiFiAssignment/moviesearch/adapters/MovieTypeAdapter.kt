package com.epiFiAssignment.moviesearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.epiFiAssignment.moviesearch.Constants
import com.epiFiAssignment.moviesearch.R
import com.epiFiAssignment.moviesearch.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.view_movie_type_btn.view.*
import java.util.zip.Inflater

class MovieTypeAdapter(
    val movieViewModel: HomeViewModel
) : RecyclerView.Adapter<MovieTypeAdapter.MovieTypeViewHolder>() {

    private val movieTypes: List<String> = Constants.movieTypes
    private val movieTypesTexts: List<String> = Constants.movieTypeTexts

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_movie_type_btn,parent , false)
        return MovieTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieTypeViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return movieTypes.size
    }

    inner class MovieTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                movieViewModel.setClickState(bindingAdapterPosition , true)
                movieViewModel.changeMovieType(movieTypes[bindingAdapterPosition])
                notifyDataSetChanged()
            }
        }
        fun bindData(position: Int){
            itemView.movie_type_btn.text = movieTypesTexts[position]

            if (movieViewModel.getClickState()[position]) handleOnMovieTypeSelected()
            else handleOnMovieTypeUnSelected()

        }
        private fun handleOnMovieTypeSelected(){
            itemView.root_view.background = itemView.context.resources.getDrawable(R.drawable.background_movie_type_button_selected_view)
            ViewCompat.setElevation(itemView.root_view , 16F)
        }

        private fun handleOnMovieTypeUnSelected(){
            itemView.root_view.background = itemView.context.resources.getDrawable(R.drawable.background_movie_type_button_unselected_view)
            ViewCompat.setElevation(itemView.root_view , 0F)
        }
    }
}
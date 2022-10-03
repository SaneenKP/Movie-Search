package com.epiFiAssignment.moviesearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.epiFiAssignment.moviesearch.R

/**
 * Loader Adapter used to showing loader during pagination
 */
class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.loader , parent , false)
        return LoaderHolder(view)
    }

    override fun onBindViewHolder(holder: LoaderHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val progress = itemView.findViewById<ProgressBar>(R.id.movieLoading)

        fun bind(loadState: LoadState){
            progress.isVisible = loadState is LoadState.Loading
        }

    }

}

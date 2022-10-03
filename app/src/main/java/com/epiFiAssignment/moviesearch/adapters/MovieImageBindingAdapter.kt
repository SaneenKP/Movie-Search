package com.epiFiAssignment.moviesearch.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.epiFiAssignment.moviesearch.utils.Constants
import com.epiFiAssignment.moviesearch.R

/**
 * Simple extension for imageview to load image from glide using path.
 */
@BindingAdapter(Constants.IMAGE_BINDING_ADAPTER_VARIABLE)
fun ImageView.imageFromImagePath(poster : String?){
    Glide.with(this.context)
        .load(poster ?: "")
        .placeholder(R.drawable.movie_image_placeholder)
        .into(this)

}

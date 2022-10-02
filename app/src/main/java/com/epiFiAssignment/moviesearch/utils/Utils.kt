package com.epiFiAssignment.moviesearch.utils

import android.content.Context
import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import com.epiFiAssignment.moviesearch.BuildConfig
import com.epiFiAssignment.moviesearch.Constants


class Utils {
    companion object{
        val DEBUG_TAG: String = "Movie_Search"
        val ERROR_TAG : String = "Error"

        fun toast(context : Context, message:String){
            Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
        }

        fun debug(message: String){
            Log.d(DEBUG_TAG , message)
        }

        fun error(message: String){
            Log.d(ERROR_TAG , message)
        }

        fun calculateRating(rating: String): Double {
            return if (rating == Constants.NOT_AVAILABLE) 0.0
            else (rating.toDouble() * 5) / 10
        }

        fun dpToPixel(context: Context , dp: Float): Float {
            val metrics: DisplayMetrics = context.resources.getDisplayMetrics()
            return dp * (metrics.densityDpi / 160f)
        }

        fun getMovieImageUrl(movieId : String) : String{
            var imageUrl : Uri? = Uri.parse(BuildConfig.IMAGE_BASE_URL)
                .buildUpon()
                .appendQueryParameter(Constants.API_KEY_QUERY , BuildConfig.API_KEY)
                .appendQueryParameter(Constants.MOVIE_ID_QUERY , movieId)
                .build()
            return imageUrl.toString()
        }
    }
}
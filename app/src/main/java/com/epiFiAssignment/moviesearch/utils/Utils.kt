package com.epiFiAssignment.moviesearch.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

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
    }
}
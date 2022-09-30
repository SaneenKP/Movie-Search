package com.epiFiAssignment.moviesearch

class Constants {

    companion object {
        const val API_KEY_QUERY = "apikey"
        const val connectionTimeOutSeconds : Long = 60;
        const val COLUMN_COUNT = 3;

        enum class Status {
            SUCCESS,
            ERROR,
            LOADING
        }
    }
}
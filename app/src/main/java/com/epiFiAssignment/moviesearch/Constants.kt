package com.epiFiAssignment.moviesearch
class Constants {

    companion object {
        const val connectionTimeOutSeconds : Long = 60;
        const val COLUMN_COUNT = 3;

        const val IMAGE_BINDING_ADAPTER_VARIABLE = "imageFromPath"

        enum class Status {
            SUCCESS,
            ERROR,
            LOADING,
            NETWORK_ERROR
        }

        //API Query Params
        const val API_KEY_QUERY = "apikey"
        const val SEARCH_QUERY = "s"
        const val TYPE_QUERY = "type"
        const val PAGE_QUERY = "page"
        const val MOVIE_ID_QUERY = "i"
    }
}
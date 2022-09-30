package com.epiFiAssignment.moviesearch.models

import com.google.gson.annotations.SerializedName


data class SearchResult (

    @SerializedName("Search"       ) var result       : ArrayList<Movie> = arrayListOf(),
    @SerializedName("totalResults" ) var totalResults : String?           = null,
    @SerializedName("Response"     ) var Response     : String?           = null

)

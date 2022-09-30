package com.epiFiAssignment.moviesearch.repository

import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.retrofit.MovieNetworkClient
import com.epiFiAssignment.moviesearch.retrofit.ResponseWrapper
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val movieNetworkClient: MovieNetworkClient
) {
    suspend fun searchMovie(
        searchQuery : String,
        page : Int,
        movieType : String
    ) : ResponseWrapper<SearchResult?> {

        return try {
            val response = movieNetworkClient.searchMovie(searchQuery , page , movieType)
            if (response.isSuccessful) {
                ResponseWrapper.success(response.body())
            }else{
                ResponseWrapper.error("Oops!! Something went wrong :(")
            }
        }catch (e : Exception){
            return ResponseWrapper.error(e.message.toString())
        }
    }
}
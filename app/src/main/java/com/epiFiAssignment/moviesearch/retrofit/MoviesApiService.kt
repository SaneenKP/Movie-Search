package com.epiFiAssignment.moviesearch.retrofit

import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.models.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET(".")
    suspend fun searchMovie(@Query("s") searchString : String , @Query("page") page : Int , @Query("type") type : String) : SearchResult

    @GET(".")
    suspend fun getMovie(@Query("i") movieId : String) : Response<Movie>

}
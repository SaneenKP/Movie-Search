package com.epiFiAssignment.moviesearch.repository

import com.epiFiAssignment.moviesearch.Constants
import com.epiFiAssignment.moviesearch.models.Movie
import com.epiFiAssignment.moviesearch.models.SearchResult
import com.epiFiAssignment.moviesearch.retrofit.MovieNetworkClient
import com.epiFiAssignment.moviesearch.retrofit.ResponseWrapper
import java.io.IOException
import javax.inject.Inject

/***
 * Repository class to call Retrofit Api.
 */

class MovieRepository @Inject constructor(
    private val movieNetworkClient: MovieNetworkClient
) {

    //Searches movie and returns the result in a response wrapper.
    suspend fun searchMovie(
        searchQuery : String,
        page : Int,
        movieType : String
    ) : ResponseWrapper<SearchResult?> {

        return try {
            val response = movieNetworkClient.searchMovie(searchQuery , page , movieType)
            if (response.isSuccessful) {
                handleSearchResponse(response.body())
            }else{
                ResponseWrapper.error(Constants.SOMETHING_WENT_WRONG_ERROR)
            }
        }catch (e : Exception){
            return ResponseWrapper.error(e.message.toString())
        }catch (e : IOException){
            return ResponseWrapper.error(e.message.toString())
        }
    }

    //gets a specific movie based on movie id and returns the result in a response wrapper.
    suspend fun getMovie(
        movieId : String
    ): ResponseWrapper<Movie?>{

        return try {
            val response = movieNetworkClient.getMovie(movieId)
            if (response.isSuccessful) {
                handleMovieResponse(response.body())
            }else{
                ResponseWrapper.error(Constants.SOMETHING_WENT_WRONG_ERROR)
            }
        }catch (e : Exception){
            return ResponseWrapper.error(e.message.toString())
        }catch (e : IOException){
            return ResponseWrapper.error(e.message.toString())
        }
    }

    /**
     * handles Response of movies and returns a response wrapper.
     * This is done since even if there are no responses of the api call , the api would still return a success.
     * This method checks if the response variable is false in the body or not. If it is then it return the error.
     * else at the end if there are no errors and the response variable is true , it meas there is a valid data and the response wrapper with success.
     */
    private fun handleMovieResponse(body: Movie?): ResponseWrapper<Movie?> {
        return if (body == null){
            ResponseWrapper.error(Constants.SOMETHING_WENT_WRONG_ERROR)
        }else if (body.Response == "False"){
            if (body.error == null) ResponseWrapper.error(Constants.SOMETHING_WENT_WRONG_ERROR)
            else ResponseWrapper.error(body.error!!)
        }else{
            ResponseWrapper.success(body)
        }
    }

    /**
     * handles Response of movies and returns a response wrapper.
     * This is done since even if there are no responses of the api call , the api would still return a success.
     * This method checks if the response variable is false in the body or not. If it is then it return the error.
     * else at the end if there are no errors and the response variable is true , it meas there is a valid data and the response wrapper with success.
     */
    private fun handleSearchResponse(body: SearchResult?) : ResponseWrapper<SearchResult> {
        return if (body == null){
            ResponseWrapper.error(Constants.SOMETHING_WENT_WRONG_ERROR)
        }else if (body.Response == "False"){
            if (body.error == null) ResponseWrapper.error(Constants.SOMETHING_WENT_WRONG_ERROR)
            else ResponseWrapper.error(body.error!!)
        }else{
            if (body.result == null) ResponseWrapper.error(Constants.NO_RESULT_ERROR)
            else ResponseWrapper.success(body)
        }
    }

}
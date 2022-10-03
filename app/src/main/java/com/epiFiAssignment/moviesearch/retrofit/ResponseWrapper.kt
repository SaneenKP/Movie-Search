package com.epiFiAssignment.moviesearch.retrofit
import com.epiFiAssignment.moviesearch.utils.Constants.Companion.Status

/**
 * A simple wrapper class to hold the state of response obtained from api calls.
 */
data class ResponseWrapper<out T>(val status : Status , val data : T? , val message : String?){

    companion object{
        fun <T> success(data: T): ResponseWrapper<T> = ResponseWrapper(status = Status.SUCCESS , data = data , message = null)
        fun <T> error(message: String): ResponseWrapper<T> = ResponseWrapper(status = Status.ERROR , data = null , message = message)
        fun <T> networkError(message: String): ResponseWrapper<T> = ResponseWrapper(status = Status.NETWORK_ERROR , data = null , message = message)
        fun <T> loading(): ResponseWrapper<T> = ResponseWrapper(status = Status.LOADING , data = null , message = null)
    }

}
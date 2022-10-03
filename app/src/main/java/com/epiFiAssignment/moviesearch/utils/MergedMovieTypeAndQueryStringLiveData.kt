package com.epiFiAssignment.moviesearch.utils

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 *  class used to merge both the searchQuery and movieType live data.
 *  This is done so that even if any one of the live data is changed , it would trigger the api call.
 */
class MergedMovieTypeAndQueryStringLiveData(
    private val searchQuery : MutableLiveData<String>,
    private val movieType : MutableLiveData<String>
): MediatorLiveData<Pair<String? , String?>>() {
    init {
        addSource(searchQuery) {value = it to movieType.value}
        addSource(movieType) {value = searchQuery.value to it}
    }
}
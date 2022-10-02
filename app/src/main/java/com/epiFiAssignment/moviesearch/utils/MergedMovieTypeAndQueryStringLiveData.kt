package com.epiFiAssignment.moviesearch.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class MergedMovieTypeAndQueryStringLiveData(
    private val searchQuery : MutableLiveData<String>,
    private val movieType : MutableLiveData<String>
): MediatorLiveData<Pair<String? , String?>>() {
    init {
        addSource(searchQuery) {value = it to movieType.value}
        addSource(movieType) {value = searchQuery.value to it}
    }
}
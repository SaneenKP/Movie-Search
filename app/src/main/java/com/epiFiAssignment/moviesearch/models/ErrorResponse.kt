package com.epiFiAssignment.moviesearch.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("Response" ) var response : String? = null,
    @SerializedName("Error"    ) var error    : String? = null,
    @SerializedName("Status"   ) var status   : Int? = null
)

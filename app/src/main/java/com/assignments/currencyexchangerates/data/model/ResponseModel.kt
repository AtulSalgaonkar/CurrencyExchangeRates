package com.assignments.currencyexchangerates.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseModel(

    @SerializedName("rates")
    @Expose
    var rates: HashMap<String, HashMap<String, Float>>? = null,

    @SerializedName("start_at")
    @Expose
    var startAt: String? = null,

    @SerializedName("base")
    @Expose
    var base: String? = null,

    @SerializedName("end_at")
    @Expose
    var endAt: String? = null

)
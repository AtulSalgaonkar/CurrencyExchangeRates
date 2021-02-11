package com.assignments.currencyexchangerates.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryExchangeRate(
    var countryRates: HashMap<String, Int>? = null
)

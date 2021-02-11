package com.assignments.currencyexchangerates.data.remote.api

import com.assignments.currencyexchangerates.data.model.ResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("history")
    fun getExchangeRatesBasedOnDateRange(
        @Query("start_at") start_at: String,
        @Query("end_at") endAt: String,
        @Query("base") base: String
    ): Single<ResponseModel>

}
package com.assignments.currencyexchangerates.data.remote

import com.assignments.currencyexchangerates.data.remote.api.APIService
import com.assignments.currencyexchangerates.utils.AppConstants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    // Get API Service Client
    val getClient: APIService
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(APIService::class.java)
        }

}
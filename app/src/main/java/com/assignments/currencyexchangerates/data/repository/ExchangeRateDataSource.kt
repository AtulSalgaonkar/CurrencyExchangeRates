package com.assignments.currencyexchangerates.data.repository

import android.content.Context
import com.assignments.currencyexchangerates.BaseApplication
import com.assignments.currencyexchangerates.data.model.ResponseModel
import com.assignments.currencyexchangerates.data.remote.APIClient
import com.assignments.currencyexchangerates.data.remote.api.APIService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.assignments.currencyexchangerates.data.model.Result
import retrofit2.Call

class ExchangeRateDataSource(
    var apiService: APIService = APIClient.getClient
) {

    // get exchange rates based on date range
    fun getExchangeRatesBasedOnDateRange(
        startDate: String,
        endDate: String,
        base: String,
        onSubscribe: (Disposable) -> Unit,
        onResponse: (Result<ResponseModel>) -> Unit
    ) {
        apiService.getExchangeRatesBasedOnDateRange(startDate, endDate, base)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ResponseModel> {
                override fun onSubscribe(d: Disposable) {
                    onSubscribe(d)
                }

                override fun onSuccess(responseModel: ResponseModel) {
                    onResponse(Result.Success(responseModel))
                }

                override fun onError(e: Throwable) {
                    onResponse(Result.Error(e))
                }
            })
    }

}
package com.assignments.currencyexchangerates.data.repository

import com.assignments.currencyexchangerates.data.model.ResponseModel
import com.assignments.currencyexchangerates.data.model.Result
import io.reactivex.disposables.Disposable

class ExchangeRateRepository(private val exchangeRateDataSource: ExchangeRateDataSource) {

    fun getExchangeRatesBasedOnDateRange(
        startDate: String = "2018-01-01",
        endDate: String = "2018-09-01",
        base: String = "USD",
        onSubscribe: (Disposable) -> Unit,
        onResponse: (Result<ResponseModel>) -> Unit
    ) {
        return exchangeRateDataSource.getExchangeRatesBasedOnDateRange(
            startDate,
            endDate,
            base,
            onSubscribe,
            onResponse,
        )
    }

}
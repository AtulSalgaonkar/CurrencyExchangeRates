package com.assignments.currencyexchangerates.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignments.currencyexchangerates.data.model.ResponseModel
import com.assignments.currencyexchangerates.data.repository.ExchangeRateDataSource
import com.assignments.currencyexchangerates.data.repository.ExchangeRateRepository
import io.reactivex.disposables.Disposable
import com.assignments.currencyexchangerates.data.model.Result

class ExchangeRateViewModel : ViewModel() {

    private var repository: ExchangeRateRepository =
        ExchangeRateRepository(ExchangeRateDataSource())

    // api response live data
    val apiCallBackLiveData: MutableLiveData<Result<HashMap<String, Float>>> = MutableLiveData()

    fun getExchangeRateData(
        selectedDate: String,
        startDate: String,
        endDate: String
    ) {
        repository.getExchangeRatesBasedOnDateRange(
            startDate,
            endDate,
            "USD",
            { disposable ->

            }, { response ->
                if (response is Result.Success) {
                    val responseModel = response.data
                    val selectedDatesData = responseModel?.rates?.get(selectedDate)
                    apiCallBackLiveData.value = Result.Success(selectedDatesData)
                } else if (response is Result.Error) {
                    apiCallBackLiveData.value = response
                }
            })
    }

}
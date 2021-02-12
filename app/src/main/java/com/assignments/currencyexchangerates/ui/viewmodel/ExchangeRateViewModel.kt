package com.assignments.currencyexchangerates.ui.viewmodel

import android.text.TextUtils
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

    private lateinit var responseModel: ResponseModel

    // api response live data
    val apiCallBackLiveData: MutableLiveData<Result<HashMap<String, Float>>> = MutableLiveData()
    val dateChangeLiveData: MutableLiveData<String> = MutableLiveData()

    fun getExchangeRateData(
        selectedDate: String,
        startDate: String,
        endDate: String,
        syncAgain: Boolean = false
    ) {
        if (this::responseModel.isInitialized && !syncAgain) {
            apiCallBackLiveData.value = Result.Success(responseModel.rates?.get(selectedDate))
        } else {
            repository.getExchangeRatesBasedOnDateRange(
                startDate,
                endDate,
                "USD",
                { disposable ->

                }, { response ->
                    if (response is Result.Success) {
                        response.data?.let {
                            responseModel = it
                            val firstKey = it.rates?.keys?.first()
                            val selectedDatesData = it.rates?.get(firstKey)
                            apiCallBackLiveData.value = Result.Success(selectedDatesData)
                            dateChangeLiveData.value = firstKey
                        }
                    } else if (response is Result.Error) {
                        apiCallBackLiveData.value = response
                    }
                })
        }
    }

}
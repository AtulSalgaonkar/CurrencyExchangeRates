package com.assignments.currencyexchangerates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.assignments.currencyexchangerates.ui.viewmodel.ExchangeRateViewModel
import com.assignments.currencyexchangerates.data.model.Result

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ExchangeRateViewModel
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ExchangeRateViewModel::class.java)

        viewModel.apiCallBackLiveData.observe(this) { resultData ->
            if (resultData is Result.Success) {
                if (resultData.data == null) {
                    Log.d(TAG, "onCreate apiCallBackLiveData: Success == null")
                } else {
                    Log.d(
                        TAG, "onCreate apiCallBackLiveData Success:\n " +
                                resultData.data
                    )
                }
            } else if (resultData is Result.Error) {
                resultData.exception.printStackTrace()
                Log.d(
                    TAG, "onCreate apiCallBackLiveData Error:\n " +
                            resultData.exception.message
                )
            }
        }

        viewModel.getExchangeRateData(
            "2018-01-02",
            "2018-01-01",
            "2018-09-01"
        )
    }
}
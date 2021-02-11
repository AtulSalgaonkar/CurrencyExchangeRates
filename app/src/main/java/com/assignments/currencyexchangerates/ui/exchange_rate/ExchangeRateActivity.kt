package com.assignments.currencyexchangerates.ui.exchange_rate

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.assignments.currencyexchangerates.R
import com.assignments.currencyexchangerates.data.model.ExchangeRate
import com.assignments.currencyexchangerates.data.model.Result
import com.assignments.currencyexchangerates.databinding.ActivityExchangeRateBinding
import com.assignments.currencyexchangerates.ui.adapter.RvGridAdapter
import com.assignments.currencyexchangerates.ui.viewmodel.ExchangeRateViewModel
import java.util.*


class ExchangeRateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeRateBinding
    private lateinit var viewModel: ExchangeRateViewModel
    private val TAG = ExchangeRateActivity::class.java.simpleName
    private lateinit var adapter: RvGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeRateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ExchangeRateViewModel::class.java)

        initRecyclerView()

        initObserver()

        binding.retryBtn.setOnClickListener {
            fetchData("", true)
        }

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view2, thisYear, thisMonth, thisDay ->
            // Display Selected date in textbox
            thisAMonth = thisMonth + 1
            thisADay = thisDay
            thisAYear = thisYear

            statusDateID.setText("Date: " + thisAMonth + "/" + thisDay + "/" + thisYear)
            val newDate:Calendar =Calendar.getInstance()
            newDate.set(thisYear, thisMonth, thisDay)
            mh.entryDate = newDate.timeInMillis // setting new date
        }, thisAYear, thisAMonth, thisADay)
        dpd.show()

        fetchData("", true)
    }

    private fun initObserver() {
        viewModel.apiCallBackLiveData.observe(this) { resultData ->
            if (resultData is Result.Success) {
                if (resultData.data == null) {
                    binding.statusTv.text = getString(R.string.no_record_found)
                    binding.statusView.visibility = View.VISIBLE
                    binding.retryBtn.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    adapter.setData(convertData(resultData.data))
                    binding.statusView.visibility = View.GONE
                    binding.retryBtn.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
            } else if (resultData is Result.Error) {
                binding.statusTv.text = getString(R.string.error_try_again)
                binding.statusView.visibility = View.VISIBLE
                binding.retryBtn.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }
    }

    private fun convertData(rateData: HashMap<String, Float>): ArrayList<ExchangeRate> {
        val data = ArrayList<ExchangeRate>()
        rateData.keys.forEach { key ->
            val rate = rateData[key]
            data.add(ExchangeRate(key, rate))
        }
        return data
    }

    private fun fetchData(selectedDateStr : String, syncAgain: Boolean = false) {
        viewModel.getExchangeRateData(
            selectedDate = selectedDateStr,
            "2018-01-01",
            "2018-09-01",
            syncAgain
        )
    }

    private fun initRecyclerView() {
        adapter = RvGridAdapter()
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.layoutManager = GridLayoutManager(this,
            resources.getInteger(R.integer.number_of_grid_items))
        binding.recyclerView.adapter = adapter
    }
}
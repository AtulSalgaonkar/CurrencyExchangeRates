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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ExchangeRateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeRateBinding
    private lateinit var viewModel: ExchangeRateViewModel
    private val TAG = ExchangeRateActivity::class.java.simpleName
    private lateinit var adapter: RvGridAdapter
    private lateinit var dpd: DatePickerDialog

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

        binding.dateEt.setOnClickListener {
            dpd.show()
        }

        fetchData("", true)

        dpd = DatePickerDialog(
            this,
            { _, thisYear, thisMonth, thisDay ->
                val thisMonthVal = thisMonth + 1
                var thisDayStr = thisDay.toString()
                if (thisDay.toString().length == 1) {
                    thisDayStr = "0$thisDay"
                }
                var thisMonthValStr = thisMonthVal.toString()
                if (thisMonthVal.toString().length == 1) {
                    thisMonthValStr = "0$thisMonthVal"
                }
                val dateStr = "$thisYear-$thisMonthValStr-$thisDayStr"
                viewModel.dateChangeLiveData.value = dateStr
            },
            2018,
            1,
            1
        )
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

        viewModel.dateChangeLiveData.observe(this) { dateString ->
            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date? = format.parse(dateString)
            val calDate = Calendar.getInstance()
            date?.let {
                calDate.time = it
                val year: Int = calDate.get(Calendar.YEAR)
                val month: Int = calDate.get(Calendar.MONTH)
                val day: Int = calDate.get(Calendar.DAY_OF_MONTH)
                dpd.updateDate(year, month, day)
                binding.dateEt.setText(dateString)
                fetchData(dateString, false)
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

    private fun fetchData(selectedDateStr: String, syncAgain: Boolean = false) {
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
        binding.recyclerView.layoutManager = GridLayoutManager(
            this,
            resources.getInteger(R.integer.number_of_grid_items)
        )
        binding.recyclerView.adapter = adapter
    }
}
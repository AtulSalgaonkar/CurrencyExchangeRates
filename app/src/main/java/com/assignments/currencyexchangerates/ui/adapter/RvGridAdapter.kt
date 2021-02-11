package com.assignments.currencyexchangerates.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignments.currencyexchangerates.data.model.ExchangeRate
import com.assignments.currencyexchangerates.databinding.ChildExchangeRateViewBinding

class RvGridAdapter : RecyclerView.Adapter<RvGridAdapter.ViewHolder>() {

    private var dataList: ArrayList<ExchangeRate>? = ArrayList()

    // set data in recyclerview
    fun setData(dataList: ArrayList<ExchangeRate>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<ExchangeRate>? {
        return dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ChildExchangeRateViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList?.get(position)
        data?.let { holder.setData(it) }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    inner class ViewHolder(bind: ChildExchangeRateViewBinding) :
        RecyclerView.ViewHolder(bind.root) {

        var viewBind: ChildExchangeRateViewBinding? = null

        init {
            viewBind = bind
        }

        fun setData(data: ExchangeRate) {
            viewBind?.countryTv?.text = data.country
            viewBind?.rateTv?.text = data.rate.toString()
        }

    }
}
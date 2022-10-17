package com.myheev.fuelmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myheev.fuelmanager.R
import com.myheev.fuelmanager.models.Prices
import com.myheev.fuelmanager.viewpager.PricesViewHolder

class PricesAdapter : RecyclerView.Adapter<PricesViewHolder>() {

    private val listPrices = mutableListOf<Prices>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PricesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prices_item, parent, false)
        return PricesViewHolder(view)
    }

    override fun getItemCount(): Int = listPrices.size

    override fun onBindViewHolder(holder: PricesViewHolder, position: Int) {
        holder.bind(listPrices[position])
    }

    fun set(list: MutableList<Prices>) {
        this.listPrices.clear()
        this.listPrices.addAll(list)
        notifyDataSetChanged()
    }
}
package com.myheev.fuelmanager.viewpager

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myheev.fuelmanager.R
import com.myheev.fuelmanager.models.Prices

class PricesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val fuelType: TextView = itemView.findViewById(R.id.tv_fuel_type)
    private val rub: TextView = itemView.findViewById(R.id.rub)
    private val usd: TextView = itemView.findViewById(R.id.usd)
    private val eur: TextView = itemView.findViewById(R.id.eur)

    fun bind(prices: Prices) {
        fuelType.text = prices.fuelType
        rub.text = prices.rub
        usd.text = prices.usd
        eur.text = prices.eur
    }
}
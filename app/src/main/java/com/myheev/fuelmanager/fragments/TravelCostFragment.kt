package com.myheev.fuelmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.myheev.fuelmanager.R
import java.text.DecimalFormat

class TravelCostFragment : Fragment() {
    private lateinit var etDistance: EditText
    private lateinit var etConsumption: EditText
    private lateinit var etPrice: EditText
    private lateinit var btnCalculateTravelCost: Button
    private lateinit var tvSumMoney: TextView
    private lateinit var sumUsedUp: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viev: View = inflater.inflate(R.layout.fragment_travel_cost, container, false)

        initialization(viev)

        btnCalculateTravelCost.setOnClickListener {
            val isCalculate = checkFullnessEditText()
            if (isCalculate) performCalculation()
        }

        return viev
    }

    private fun performCalculation() {
        val distance = etDistance.text.toString()
        val consumption = etConsumption.text.toString()
        val price = etPrice.text.toString()
        tvSumMoney.text =
            calculateSum(distance.toInt(), consumption.toDouble(), price.toDouble())
        sumUsedUp.text =
            calculateSum(distance.toInt(), consumption.toDouble(), 1.0)
    }

    private fun calculateSum(distance: Int, consumption: Double, price: Double) =
        DecimalFormat(".00").format(distance * consumption * price / 100)

    private fun checkFullnessEditText(): Boolean = etDistance.text.toString().isNotEmpty() &&
            etConsumption.text.toString().isNotEmpty() &&
            etPrice.text.toString().isNotEmpty()

    private fun initialization(viev: View) {
        etDistance = viev.findViewById(R.id.distance) as EditText
        etConsumption = viev.findViewById(R.id.consumption) as EditText
        etPrice = viev.findViewById(R.id.price) as EditText
        btnCalculateTravelCost = viev.findViewById(R.id.calculate) as Button
        tvSumMoney = viev.findViewById(R.id.sum_money) as TextView
        sumUsedUp = viev.findViewById(R.id.sum_used_up)
    }

}
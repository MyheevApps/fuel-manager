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

class ConsumptionFragment : Fragment() {

    private lateinit var etSpentFuel: EditText
    private lateinit var etDistanceCovered: EditText
    private lateinit var btnCalculateConsumption: Button
    private lateinit var tvSumOfMoney: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_consumption, container, false)
        initialization(view)

        btnCalculateConsumption.setOnClickListener {
            val isCalculate = checkFullnessEditText()
            if (isCalculate) performCalculation()
        }
        return view
    }

    private fun checkFullnessEditText(): Boolean = etDistanceCovered.text.toString().isNotEmpty() &&
            etSpentFuel.text.toString().isNotEmpty()

    private fun performCalculation() {
        val distance = etDistanceCovered.text.toString()
        val spentFuel = etSpentFuel.text.toString()
        tvSumOfMoney.text = calculateConsumption(distance.toInt(), spentFuel.toDouble())
    }

    private fun initialization(view: View) {
        etSpentFuel = view.findViewById(R.id.spent_fuel) as EditText
        etDistanceCovered = view.findViewById(R.id.distance_covered) as EditText
        btnCalculateConsumption = view.findViewById(R.id.calculate_consumption) as Button
        tvSumOfMoney = view.findViewById(R.id.sum_of_money) as TextView
    }

    private fun calculateConsumption(distance: Int, consumption: Double) =
        DecimalFormat(".00").format(consumption / distance * 100)
}
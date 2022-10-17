package com.myheev.fuelmanager.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myheev.fuelmanager.R
import com.myheev.fuelmanager.adapters.PricesAdapter
import com.myheev.fuelmanager.models.Prices
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException

class PricesFragment : Fragment() {

    private val url = "https://driff.ru/auto/fuel-dynamics.html"
    private val listPrices = mutableListOf<Prices>()
    private lateinit var rvPricesList: RecyclerView
    private var adapter: PricesAdapter = PricesAdapter()
    private lateinit var tvData: TextView
    private lateinit var tvInternetIsNotConnect: TextView
    private lateinit var cvPrices: CardView

    @RequiresApi(Build.VERSION_CODES.M)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viev = inflater.inflate(R.layout.fragment_prices, container, false)

        initialization(viev)
        configureListPrices()

        if (isNetworkConnected(viev.context)) GlobalScope.launch { getData() }
        else showNotice()

        return viev
    }

    private fun showNotice() {
        cvPrices.isVisible = false
        tvData.isVisible = false
    }

    private fun configureListPrices() {
        listPrices.clear()
        rvPricesList.layoutManager = LinearLayoutManager(this.context)
        rvPricesList.adapter = adapter
    }

    private fun initialization(v: View) {
        rvPricesList = v.findViewById(R.id.rv_prices_list)
        cvPrices = v.findViewById(R.id.cv_prices)
        tvData = v.findViewById(R.id.tv_data)
        tvInternetIsNotConnect = v.findViewById(R.id.tv_internet_is_not_connect)
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isDataNotEmpty(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getData() {
        try {
            val document = Jsoup.connect(url).get()
            val elements = document.select("table[class=standart]").select("tbody").select("tr")
            val data = document.select("div[class=section]").select("h2").first().text()

            for (i in 0 until elements.size) {
                val element = elements.eq(i).select("td")
                listPrices.add(
                    Prices(
                        element.get(0).text(),
                        element.get(1).text(),
                        element.get(2).text(),
                        element.get(3).text()
                    )
                )
            }

            GlobalScope.launch(Dispatchers.Main) {
                adapter.set(listPrices)
                tvData.text = data
            }

        } catch (e: IOException) {

        }
    }

}
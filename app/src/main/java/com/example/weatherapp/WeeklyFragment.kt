package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.CustomAdapter
import com.example.weatherapp.utlis.LocationData
import com.example.weatherapp.databinding.FragmentWeeklyBinding
import com.example.weatherapp.model.DataDailyModel
import com.example.weatherapp.model.data.Forecast
import com.example.weatherapp.viewmodels.CurrentViewModel
import retrofit2.Call
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class WeeklyFragment : Fragment() {

    lateinit var viewModel: CurrentViewModel
    lateinit var binding: FragmentWeeklyBinding

    val NUM_DAYS = 7
    var description: String? = null
    var minTemp: String? = null ; var maxTemp: String? = null
    var pressure: Float? = 0.0f ; var humidity: Int? = 0
    var wind: Float? = 0.0f ; var rain: Float? = 0.0f
    var idIcon: Int? = 0 ; var idBackground: Int? = 0
    private var progressBar:ProgressBar ?= null
    private lateinit var customAdapter: CustomAdapter
    private var recyclerView: RecyclerView? = null
    private var apiInterface: Call<Forecast>? = null
    private var dailyList = ArrayList<DataDailyModel>()
    lateinit var locData: LocationData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        customAdapter = CustomAdapter(dailyList)
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = customAdapter
        progressBar = view.findViewById(R.id.progressBar2)

        locData = LocationData()
        //getWeeklyData()
    }

    /*private fun getWeeklyData() {
        apiInterface = InterfaceAPI.create().getCurrentWeatherData(locData.setDefaultLatitude(), locData.setDefaultLongitude(), locData.apiKey())
        apiInterface!!.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    progressBar?.visibility = View.GONE
                    for (i in 1..NUM_DAYS) {
                        fetchDailyWeather(response.body()!!, i)
                    }
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
            }
        })
    }*/

    private fun fetchDailyWeather(body: Forecast?, i: Int) {
        val sdf = SimpleDateFormat("EEEE")
        var dayOfTheWeek: String ?= null
        var time= Timestamp(body!!.daily[i].dt*1000)

        dayOfTheWeek = sdf.format(time)
        idIcon = locData.fetchIcon(body.daily[i].weather[0].id)
        idBackground = locData.fetchBackground(body.daily[i].weather[0].id)

        description = body.daily[i].weather[0].description
        minTemp = "" + locData.kelvinToCelsius(body.daily[i].temp.min) + "°"
        maxTemp = "" + locData.kelvinToCelsius(body.daily[i].temp.max) + "°"
        pressure = body.daily[i].pressure
        humidity = body.daily[i].humidity
        wind = body.daily[i].wind_speed
        rain = body.daily[i].rain

        dailyList.add(
            DataDailyModel(description!!, dayOfTheWeek, idIcon!!, minTemp!!, maxTemp!!, rain!!, humidity!!, wind!!, pressure!!, idBackground!!,)
        )
        customAdapter.notifyDataSetChanged()
    }
}

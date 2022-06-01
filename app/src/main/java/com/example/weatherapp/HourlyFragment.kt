package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.CustomHourlyAdapter
import com.example.weatherapp.api.*
import com.example.weatherapp.data.LocationData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.ArrayList

class HourlyFragment : Fragment() {

    private val NUM_HOURS = 24
    private lateinit var customAdapter: CustomHourlyAdapter
    private var recyclerView: RecyclerView? = null
    private var hourlyList = ArrayList<DataHourlyModel>()
    private var hourlyDescription:String ?= null
    private var hourlyTemp: String ?= null
    private var background: FrameLayout ?= null
    private var progressBar: ProgressBar ?= null
    private var hConst: ConstraintLayout ?= null
    lateinit var locData: LocationData
    private var apiInterface:Call<Forecast> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hourly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.hourlyRecyclerView)
        customAdapter = CustomHourlyAdapter(hourlyList)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.adapter = customAdapter
        progressBar = view.findViewById(R.id.progressBar3)
        background = view.findViewById(R.id.hourlyFrame)
        hConst = view.findViewById(R.id.hConst)

        locData = LocationData()
        fetchCurrentLocationWeather(progressBar!!)
    }

    private fun fetchCurrentLocationWeather(progressBar: ProgressBar) {
        apiInterface = InterfaceAPI.create().getCurrentWeatherData(locData.setDefaultLatitude(),locData.setDefaultLongitude(),locData.apiKey())
        apiInterface!!.enqueue(object: Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful){
                    for (i in 1..NUM_HOURS) {
                        progressBar.visibility = View.GONE
                        hConst?.visibility = View.VISIBLE
                        fetchHourlyWeather(response.body()!!, i)
                    }
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun fetchHourlyWeather(body: Forecast, i: Int) {
        val sdf = SimpleDateFormat("HH")
        var hour: String ?= null
        var time = Timestamp(body.hourly[i].dt*1000)
        hour = sdf.format(time) + ":00h"
        hourlyDescription = body.hourly[i].weather[0].description
        hourlyTemp = "" + locData.kelvinToCelsius((body.hourly[i].temp)) + "°"
        locData.fetchIcon(body.hourly[i].weather[0].id)
        locData.fetchBackground(body.current.weather[0].id)
        background?.setBackgroundResource(locData.idCurrentBack)
        hourlyList.add(
            DataHourlyModel(
                hour,
                locData.idHourlyIcon!!,
                hourlyDescription!!,
                hourlyTemp!!,
            )
        )
        customAdapter.notifyDataSetChanged()
    }
}
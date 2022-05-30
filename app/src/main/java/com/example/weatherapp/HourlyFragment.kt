package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.ArrayList

class HourlyFragment : Fragment() {

    val defaultLatitude: Float = 45.815399f
    val defaultLongitude: Float = 15.966568f
    private val API_KEY = "d32c530968b46cca52ed08edcf0d6a93"
    val NUM_HOURS = 24

    private lateinit var customAdapter: CustomHourlyAdapter
    private var recyclerView: RecyclerView? = null
    private var hourlyList = ArrayList<DataHourlyModel>()
    private var apiInterface: Call<Forecast>? = null

    private var hourlyDescription:String ?= null
    private var hourlyTemp: String ?= null
    private var idHourlyIcon: Int ?= 0
    private var background: FrameLayout ?= null
    private var idCurrentBack: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hourly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view?.findViewById(R.id.hourlyRecyclerView)
        customAdapter = CustomHourlyAdapter(hourlyList)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.adapter = customAdapter

        background = view.findViewById(R.id.hourlyFrame)

        getHourlyData()

    }

    private fun getHourlyData() {
        apiInterface = InterfaceAPI.create().getCurrentWeatherData(defaultLatitude, defaultLongitude, API_KEY)
        apiInterface!!.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    for (i in 1..NUM_HOURS) {
                        fetchHourlyWeather(response.body()!!, i)
                        Log.d("fetched", i.toString())
                    }
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d("error_retrofit: ", t.toString())
            }
        })
    }

    private fun fetchHourlyWeather(body: Forecast?, i: Int) {
        val sdf = SimpleDateFormat("HH")
        var hour: String ?= null
        var time: Timestamp = Timestamp(body!!.hourly[i].dt*1000)
        hour = sdf.format(time) + ":00h"

        hourlyDescription = body.hourly[i].weather[0].description
        hourlyTemp = "" + kelvinToCelsius(body.hourly[i].temp) + "°"

        fetchIcon(body.hourly[i].weather[0].id)
        fetchBackground(body.current.weather[0].id)

        background?.setBackgroundResource(idCurrentBack)
        hourlyList.add(
            DataHourlyModel(
                hour,
                idHourlyIcon!!,
                hourlyDescription!!,
                hourlyTemp!!,
            )
        )

        customAdapter.notifyDataSetChanged()
    }

    private fun kelvinToCelsius(temp: Float): Any? {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP)
    }

    private fun fetchIcon(id: Int){
        if(id in 201..232){
            idHourlyIcon = R.drawable.thunderstorm_icon
        }
        else if(id in 500..531 || id in 300..321){
            idHourlyIcon = R.drawable.rain_transparent
        }
        else if(id in 600..622){
            idHourlyIcon = R.drawable.snow_transparent
        }
        else if(id == 800){
            idHourlyIcon = R.drawable.sun_trasparent
        }
        else if(id in 801..804){
            idHourlyIcon = R.drawable.clouds_transparent
        }
    }
    private fun fetchBackground(id: Int){
        if(id in 201..232){
            idCurrentBack = R.drawable.thunderstorm_background
        }
        else if(id in 500..531 || id in 300..321){
            idCurrentBack = R.drawable.rain_background
        }
        else if(id in 600..622){
            idCurrentBack = R.drawable.snow_background
        }
        else if(id == 800){
            idCurrentBack = R.drawable.clear_background
        }
        else if(id in 801..804){
            idCurrentBack = R.drawable.snow_background
        }
    }

}
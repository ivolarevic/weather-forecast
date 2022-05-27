package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.InterfaceAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


// Default fragment for city of Zagreb

class CurrentFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var currentTime:TextView ?= null
    private var minTemp:TextView ?= null
    private var maxTemp:TextView ?= null
    private var currentTemp:TextView ?= null
    private var cityName:TextView ?= null
    private var currentIcon:ImageView ?= null
    private var currentHumidity:TextView ?= null
    private var currentVisibility:TextView ?= null
    private var currentWind:TextView ?= null
    private var currentFeel:TextView ?= null
    private var currentType:TextView ?= null

    private val PATH_URL = "/data/2.5/onecall"
    private val BASE_URL = "https://api.openweathermap.org"
    private val API_KEY = "d32c530968b46cca52ed08edcf0d6a93"

    private var apiInterface:Call<Forecast> ?= null

    // Zagreb
     val defaultLatitude:Float = 45.815399f
     val defaultLongitude:Float = 15.966568f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_current, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentTime = view?.findViewById(R.id.time)
        minTemp = view?.findViewById(R.id.min_temp)
        maxTemp = view?.findViewById(R.id.max_temp)
        currentTemp = view?.findViewById(R.id.temp)
        currentFeel = view?.findViewById(R.id.feels_like)
        currentHumidity =  view?.findViewById(R.id.humidity)
        currentWind =  view?.findViewById(R.id.wind)
        currentVisibility =  view?.findViewById(R.id.visibility)
        currentIcon =  view?.findViewById(R.id.weather_now)
        currentType = view?.findViewById(R.id.weater_type)
        cityName = view?.findViewById(R.id.cityName)

        fetchCurrentLocationWeather(defaultLatitude, defaultLongitude)

    }


    private fun fetchCurrentLocationWeather(latitude: Float, longitude: Float){

        apiInterface = InterfaceAPI.create().getCurrentWeatherData(defaultLatitude,defaultLongitude,API_KEY)
        apiInterface!!.enqueue(object: Callback<Forecast>{

            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful){
                    setDataOnCurrentFragment(response.body()!!)
                    Log.d("success: ", "Retrofit je prosao")
                    Log.d("response", response.body()!!.current.feels_like.toString())
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d("error_retrofit: ", t.toString())
            }

        })

    }

    private fun setDataOnCurrentFragment(body: Forecast?){

        val sdf = SimpleDateFormat("HH:mm:ss, dd.MM.yyyy")
        val currentDate = sdf.format(Date())
        currentTime?.text = currentDate
        cityName?.text = "Zagreb"

        currentTemp?.text = "" + kelvinToCelsius(body!!.current.temp) + "°"
        currentFeel?.text = "Feels like: " + kelvinToCelsius(body!!.current.feels_like) + "°"

        currentType?.text = body.current.weather[0].main

        currentHumidity?.text = body.current.humidity.toString()+"%"
        currentWind?.text = body.current.wind_speed.toString() + " m/s"

        minTemp?.text = "Min.temp: " + kelvinToCelsius(body.daily[0].temp.min) + "°"
        maxTemp?.text = "Max.temp: " + kelvinToCelsius(body.daily[0].temp.max) + "°"

        // updateUI(body.weather[0].id)


    }

    private fun kelvinToCelsius(temp: Float): Any? {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }


}
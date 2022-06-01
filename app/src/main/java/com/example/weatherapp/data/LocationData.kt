package com.example.weatherapp.data

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.api.Forecast
import com.example.weatherapp.api.InterfaceAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode

class LocationData {

    private val API_KEY = "d32c530968b46cca52ed08edcf0d6a93"
    private var apiInterface:Call<Forecast> ?= null
    var idHourlyIcon: Int ?= 0
    var idCurrentBack: Int = 0
    val defaultLatitude = 45.815399f
    val defaultLongitude = 15.966568f

    fun fetchCurrentLocationWeather(): Forecast? {
        var forecast : Forecast ?= null
        apiInterface = InterfaceAPI.create().getCurrentWeatherData(defaultLatitude,defaultLongitude,API_KEY)
        apiInterface!!.enqueue(object: Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful){
                    forecast = response.body()!!
                    Log.d("succ: ", forecast!!.lon.toString())

                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d("error_retrofit: ", t.toString())
            }

        })
        return forecast
    }

    public fun kelvinToCelsius(temp: Float): Any? {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP)
    }

    public fun fetchIcon(id: Int){
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
    public fun fetchBackground(id: Int){
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
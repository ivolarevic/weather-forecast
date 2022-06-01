package com.example.weatherapp.data

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.api.Forecast
import com.example.weatherapp.api.InterfaceAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode

open class LocationData() {

    val API_KEY = "d32c530968b46cca52ed08edcf0d6a93"
    var idHourlyIcon: Int = 0
    var idCurrentBack: Int = 0
    val defaultLatitude:Float = 45.815399f
    val defaultLongitude:Float = 15.966568f

    fun setDefaultLatitude() : Float {
        return defaultLatitude
    }
    fun setDefaultLongitude() : Float {
        return defaultLongitude
    }
    fun apiKey() : String{
        return "d32c530968b46cca52ed08edcf0d6a93"
    }

    fun kelvinToCelsius(temp: Float): Any? {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP)
    }

    fun fetchIcon(id: Int) : Int {
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
        return idHourlyIcon
    }

    fun fetchBackground(id: Int) : Int {
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
        return idCurrentBack
    }

    fun animateImage(image: ImageView?){
        image!!.animate().apply {
            duration = 1000
            rotationYBy(360f)
        }.start()
    }
}
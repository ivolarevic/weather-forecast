package com.example.weatherapp.data

import android.media.Image
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.InterfaceAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode

class LocationData {

    private val PATH_URL = "/data/2.5/onecall"
    private val BASE_URL = "https://api.openweathermap.org"
    private val API_KEY = "d32c530968b46cca52ed08edcf0d6a93"
    private var apiInterface:Call<Forecast> ?= null

    // Zagreb
    public val defaultLatitude = 45.815399f
    public val defaultLongitude = 15.966568f


    public fun fetchCurrentLocationWeather(progressBar: ProgressBar, view: View): Forecast? {
        var forecast : Forecast ?= null
        apiInterface = InterfaceAPI.create().getCurrentWeatherData(defaultLatitude,defaultLongitude,API_KEY)
        apiInterface!!.enqueue(object: Callback<Forecast> {

            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful){
                    forecast = response.body()
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d("error_retrofit: ", t.toString())
            }

        })
        return forecast
    }

    public fun updateUI(id: Int, weatherIcon: ImageView?, view: View?){
        var idIcon:Int = 0
        var idBackground:Int = 0

        // Thunderstorm
        if(id in 201..232){
            idBackground = R.drawable.thunderstorm_background
            idIcon = R.drawable.thunderstorm_icon
        }

        // Rain and Drizzle
        else if(id in 500..531 || id in 300..321){
            idBackground = R.drawable.rain_background
            idIcon = R.drawable.rain_transparent
        }
        // Snow
        else if(id in 600..622){
            idBackground = R.drawable.snow_background
            idIcon = R.drawable.snow_transparent
        }
        // Clear
        else if(id == 800){
            idBackground = R.drawable.clear_background
            idIcon = R.drawable.sun_trasparent
        }
        // Clouds
        else if(id in 801..804){
            idBackground = R.drawable.snow_background
            idIcon = R.drawable.clouds_transparent
        }
        // Atmosphere
        /*else if(id in 701..781){
            idBackground = R.drawable.clear_background
            idIcon = R.drawable.sun
        }*/

        updateIcon(weatherIcon, idIcon, idBackground, view!!)
    }

    public fun updateIcon(image: ImageView?, idIcon:Int, idBackground:Int, view: View){
        view.setBackgroundResource(idBackground)
        Glide.with(view).load(idIcon).into(image!!);

        image!!.animate().apply {
            duration = 1000
            rotationYBy(360f)
        }.start()
    }



}
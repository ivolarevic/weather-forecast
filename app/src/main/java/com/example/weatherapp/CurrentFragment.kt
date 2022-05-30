package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.weatherapp.data.LocationData

import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.InterfaceAPI
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView
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
    private var currentHumidity:TextView ?= null
    private var currentVisibility:TextView ?= null
    private var currentWind:TextView ?= null
    private var currentFeel:TextView ?= null
    private var currentType:TextView ?= null
    private var pressure:TextView ?= null
    private var weatherIcon:ImageView ?= null

    private var background:ConstraintLayout?=null

    val sdf = SimpleDateFormat("HH:mm:ss, dd.MM.yyyy")
    val currentDate = sdf.format(Date())

    private val PATH_URL = "/data/2.5/onecall"
    private val BASE_URL = "https://api.openweathermap.org"
    private val ICON_URL = "http://openweathermap.org/img/wn/"
    private val API_KEY = "d32c530968b46cca52ed08edcf0d6a93"
    private var URL:String = ""
    var progressBar: ProgressBar ?= null
    var linearH1: LinearLayout ?= null
    var linearH2 : LinearLayout ?= null

    private var apiInterface:Call<Forecast> ?= null
    // private var weatherView: WeatherView?= null

    // Zagreb
     val defaultLatitude:Float = 45.815399f
     val defaultLongitude:Float = 15.966568f

    val locationData: LocationData ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearH1 = view.findViewById(R.id.linear_horizontal)
        linearH2 = view.findViewById(R.id.linear_horizontal2)
        currentTime = view.findViewById(R.id.time)
        minTemp = view.findViewById(R.id.min_temp)
        maxTemp = view.findViewById(R.id.max_temp)
        currentTemp = view.findViewById(R.id.temp)
        currentFeel = view.findViewById(R.id.feels_like)
        currentHumidity =  view.findViewById(R.id.humidity)
        currentWind =  view.findViewById(R.id.wind)
        currentVisibility =  view.findViewById(R.id.visibility)
        currentType = view.findViewById(R.id.weater_type)
        cityName = view.findViewById(R.id.cityName)
        pressure = view.findViewById(R.id.pressure)
        weatherIcon = view.findViewById(R.id.weather_now)
        background = view.findViewById(R.id.currentConstraint)
        progressBar = view.findViewById(R.id.progressBar)

        fetchCurrentLocationWeather(progressBar!!)
    }

    public fun fetchCurrentLocationWeather(progressBar: ProgressBar) {
        apiInterface = InterfaceAPI.create().getCurrentWeatherData(defaultLatitude,defaultLongitude,API_KEY)
        apiInterface!!.enqueue(object: Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful){
                    progressBar.visibility = View.GONE
                    linearH1?.visibility = View.VISIBLE
                    linearH2?.visibility = View.VISIBLE
                    setDataOnCurrentFragment(response.body()!!)
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d("error_retrofit: ", t.toString())
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setDataOnCurrentFragment(body: Forecast?){
        currentTime?.text = currentDate
        cityName?.text = "Zagreb"
        currentTemp?.text = "" + kelvinToCelsius(body!!.current.temp) + "°C"
        currentFeel?.text = "Feels like: " + kelvinToCelsius(body.current.feels_like) + "°"
        currentType?.text = body.current.weather[0].main
        currentHumidity?.text = body.current.humidity.toString()+"%"
        currentWind?.text = body.current.wind_speed.toString() + "m/s"
        var visibility = body.current.visibility.div(1000) //km
        currentVisibility?.text = visibility.toString() + "km"
        minTemp?.text = "" + kelvinToCelsius(body.daily[0].temp.min) + "°"
        maxTemp?.text = "" + kelvinToCelsius(body.daily[0].temp.max) + "°"
        pressure?.text = body.current.pressure.toString() + "hPa"

        val id = body.current.weather[0].id
        updateUI(id)

        /* Icons from web API
        var icon = body.current.weather[0].icon
        URL = ICON_URL + icon + ".png"*/
    }

    private fun kelvinToCelsius(temp: Float): Any? {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP)
    }

    private fun updateUI(id: Int){
        var idIcon:Int = 0
        var idBackground:Int = 0

        if(id in 201..232){
            idBackground = R.drawable.thunderstorm_background
            idIcon = R.drawable.thunderstorm_icon
        }
        else if(id in 500..531 || id in 300..321){
            idBackground = R.drawable.rain_background
            idIcon = R.drawable.rain_transparent
        }
        else if(id in 600..622){
            idBackground = R.drawable.snow_background
            idIcon = R.drawable.snow_transparent
        }
        else if(id == 800){
            idBackground = R.drawable.clear_background
            idIcon = R.drawable.sun_trasparent
        }
        else if(id in 801..804){
            idBackground = R.drawable.snow_background
            idIcon = R.drawable.clouds_transparent
        }
        // Atmosphere
        /*else if(id in 701..781){
            idBackground = R.drawable.clear_background
            idIcon = R.drawable.sun
        }*/
        updateIcon(weatherIcon, idIcon, idBackground)
    }

    private fun updateIcon(image: ImageView?, idIcon:Int, idBackground:Int){
        background?.setBackgroundResource(idBackground)
        Glide.with(this).load(idIcon).into(weatherIcon!!);
        image!!.animate().apply {
            duration = 1000
            rotationYBy(360f)
        }.start()
    }
}
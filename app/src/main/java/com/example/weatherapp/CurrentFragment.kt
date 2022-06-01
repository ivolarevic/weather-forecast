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
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.weatherapp.data.LocationData
import com.example.weatherapp.api.Forecast
import com.example.weatherapp.api.InterfaceAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

// Default fragment for city of Zagreb
class CurrentFragment : Fragment() {
    private var currentTime:TextView ?= null
    private var minTemp:TextView ?= null  ; private var maxTemp:TextView ?= null
    private var currentTemp:TextView ?= null ; private var cityName:TextView ?= null
    private var currentHumidity:TextView ?= null  ; private var currentVisibility:TextView ?= null
    private var currentWind:TextView ?= null ; private var currentFeel:TextView ?= null
    private var currentType:TextView ?= null  ;private var pressure:TextView ?= null
    private var weatherIcon:ImageView ?= null ; private var background:ConstraintLayout?=null
    private var apiInterface:Call<Forecast> ?= null
    var progressBar: ProgressBar ?= null
    lateinit var locData: LocationData
    var linearH1: LinearLayout ?= null
    var linearH2 : LinearLayout ?= null

    val sdf = SimpleDateFormat("HH:mm:ss, dd.MM.yyyy")
    val currentDate = sdf.format(Date())
    // private var weatherView: WeatherView?= null

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

        locData = LocationData()
        fetchCurrentLocationWeather(progressBar!!)
    }

     private fun fetchCurrentLocationWeather(progressBar: ProgressBar) {
        apiInterface = InterfaceAPI.create().getCurrentWeatherData(locData.setDefaultLatitude(),locData.setDefaultLongitude(),locData.apiKey())
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
        currentTemp?.text = "" + locData.kelvinToCelsius(body!!.current.temp) + "°C"
        currentFeel?.text = "Feels like: " + locData.kelvinToCelsius(body.current.feels_like) + "°"
        currentType?.text = body.current.weather[0].main
        currentHumidity?.text = body.current.humidity.toString()+"%"
        currentWind?.text = body.current.wind_speed.toString() + "m/s"
        minTemp?.text = "" + locData.kelvinToCelsius(body.daily[0].temp.min) + "°"
        maxTemp?.text = "" + locData.kelvinToCelsius(body.daily[0].temp.max) + "°"
        pressure?.text = body.current.pressure.toString() + "hPa"

        var visibility = body.current.visibility.div(1000) //km
        currentVisibility?.text = visibility.toString() + "km"

        val id = body.current.weather[0].id
        background?.setBackgroundResource(locData.fetchBackground(id))
        Glide.with(this).load(locData.fetchIcon(id)).into(weatherIcon!!);

        locData.animateImage(weatherIcon)
    }
}
/* Icons from web API
        var icon = body.current.weather[0].icon
        URL = ICON_URL + icon + ".png"*/
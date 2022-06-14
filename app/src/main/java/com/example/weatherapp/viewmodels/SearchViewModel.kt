package com.example.weatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.data.Forecast

import com.example.weatherapp.utlis.LocationData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat

class SearchViewModel : ViewModel() {
    var locData: LocationData = LocationData()
    var apiInterface: Call<Forecast>? = null
    var humidity : String = ""
    var cityNameView: String = ""
    var cityName : String = ""
    var temp: String = ""
    var description: String = ""
    var minTemp: String = ""
    var maxTemp: String = ""
    var pressure: String = ""
    var wind: String = ""
    var visibility: String = ""
    var id: Int = 0

     /*fun fetchData(city : String){
        cityName = city
        locData.getCityCoordinates(city)

        apiInterface = InterfaceAPI.create().getCurrentWeatherData(locData.setDefaultLatitude(),locData.setDefaultLongitude(),locData.apiKey())
        apiInterface!!.enqueue(object: Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful){
                    cityData(response.body()!!)
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d("error_retrofit: ", t.toString())
            }
        })
    }*/

    fun cityData(body: Forecast){
        id = body.current.weather[0].id
        humidity = body.current.humidity.toString()+"%"
        temp = "" + locData.kelvinToCelsius(body.current.temp) + "°"
        minTemp =  "" + locData.kelvinToCelsius(body.daily[0].temp.min) + "°"
        maxTemp =  "" + locData.kelvinToCelsius(body.daily[0].temp.max) + "°"
        description = body.current.weather[0].main
        wind = body.current.wind_speed.toString() + "m/s"
        pressure = body.current.pressure.toString() + "hPa"
        visibility = body.current.visibility.div(1000).toString() + "km"

        val sdf = SimpleDateFormat("HH")
        val time = Timestamp(body.hourly[0].dt*1000)
        val hour = sdf.format(time)
    }

}
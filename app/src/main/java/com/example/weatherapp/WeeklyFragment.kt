package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.model.DataDailyModel
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.InterfaceAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class WeeklyFragment : Fragment() {

    val defaultLatitude: Float = 45.815399f
    val defaultLongitude: Float = 15.966568f
    private val API_KEY = "d32c530968b46cca52ed08edcf0d6a93"
    val NUM_DAYS = 7

    private var progressBar:ProgressBar ?= null
    private lateinit var customAdapter: CustomAdapter
    private var recyclerView: RecyclerView? = null

    private var apiInterface: Call<Forecast>? = null
    var description: String? = null
    var minTemp: String? = null
    var maxTemp: String? = null
    var dayOfWeek: String = "Zagreb"
    var pressure: Float? = 0.0f
    var humidity: Int? = 0
    var wind: Float? = 0.0f
    var rain: Float? = 0.0f
    var idIcon: Int? = 0
    var idBackground: Int? = 0

    private var dailyList = ArrayList<DataDailyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view?.findViewById(R.id.recyclerView)
        customAdapter = CustomAdapter(dailyList)
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = customAdapter
        progressBar = view?.findViewById(R.id.progressBar2)

        getWeeklyData()
    }

    private fun getWeeklyData() {
        apiInterface = InterfaceAPI.create().getCurrentWeatherData(defaultLatitude, defaultLongitude, API_KEY)
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
                Log.d("error_retrofit: ", t.toString())
            }
        })
    }

    private fun fetchDailyWeather(body: Forecast?, i: Int) {

        val sdf = SimpleDateFormat("EEEE")
        var dayOfTheWeek: String ?= null

        var time:Timestamp = Timestamp(body!!.daily[i].dt*1000)
        dayOfTheWeek = sdf.format(time)

        fetchIcon(body.daily[i].weather[0].id)

        description = body.daily[i].weather[0].description
        minTemp = "" + kelvinToCelsius(body.daily[i].temp.min) + "°"
        maxTemp = "" + kelvinToCelsius(body.daily[i].temp.max) + "°"
        pressure = body.daily[i].pressure
        humidity = body.daily[i].humidity
        wind = body.daily[i].wind_speed
        rain = body.daily[i].rain

        dailyList.add(
            DataDailyModel(
                description!!,
                dayOfTheWeek,
                idIcon!!,
                minTemp!!,
                maxTemp!!,
                rain!!,
                humidity!!,
                wind!!,
                pressure!!,
                idBackground!!,
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
    }


}

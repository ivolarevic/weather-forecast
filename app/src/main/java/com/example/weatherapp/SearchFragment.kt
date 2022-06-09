package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.weatherapp.api.Forecast
import com.example.weatherapp.api.InterfaceAPI
import com.example.weatherapp.data.LocationData
import com.example.weatherapp.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var locData: LocationData
    private var apiInterface: Call<Forecast>? = null
    private lateinit var humidity : TextView
    private lateinit var cityNameView: TextView
    private var nameCity : String ?= null
    private lateinit var searchTempView: TextView
    private lateinit var searchBackgroundView: FrameLayout
    private lateinit var searchIconView: ImageView
    private lateinit var searchDescriptionView: TextView
    private lateinit var minSearch: TextView
    private lateinit var maxSearch: TextView
    private lateinit var pressureSearch: TextView
    private lateinit var windSearch: TextView
    private lateinit var visibilitySearch: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        humidity = binding.searchHumidity
        cityNameView = binding.searchName
        searchTempView = binding.searchTemp
        searchBackgroundView = binding.searchBackground
        searchIconView = binding.searchIcon
        searchDescriptionView = binding.searchDescription
        minSearch = binding.searchMinTemp
        maxSearch = binding.searchMaxTemp
        pressureSearch = binding.searchPressure
        windSearch = binding.windSearch
        visibilitySearch = binding.visibilitySearch

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = binding.spinner
        locData = LocationData()

        ArrayAdapter.createFromResource(
            view.context,
            R.array.cities_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        fetchSpinnerData(spinner)

    }

    private fun fetchSpinnerData(spinner:Spinner){
        val cities = resources.getStringArray(R.array.cities_array)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                showCityWeather(cities[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                showCityWeather("Zagreb")
            }
        }
    }

    private fun showCityWeather(cityName : String) {
        nameCity = cityName
        locData.getCityCoordinates(cityName)

        apiInterface = InterfaceAPI.create().getCurrentWeatherData(locData.setDefaultLatitude(),locData.setDefaultLongitude(),locData.apiKey())
        apiInterface!!.enqueue(object: Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful){
                    showCityWeather(response.body()!!)
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d("error_retrofit: ", t.toString())
            }
        })
    }


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun showCityWeather(body:Forecast){
        val id = body.current.weather[0].id
        humidity.text = body.current.humidity.toString()+"%"
        cityNameView.text = nameCity
        searchTempView.text = "" + locData.kelvinToCelsius(body.current.temp) + "°"
        minSearch.text =  "" + locData.kelvinToCelsius(body.daily[0].temp.min) + "°"
        maxSearch.text =  "" + locData.kelvinToCelsius(body.daily[0].temp.max) + "°"
        searchDescriptionView.text = body.current.weather[0].main
        windSearch.text = body.current.wind_speed.toString() + "m/s"
        pressureSearch.text = body.current.pressure.toString() + "hPa"
        visibilitySearch.text = body.current.visibility.div(1000).toString() + "km"

        val sdf = SimpleDateFormat("HH")
        val time = Timestamp(body.hourly[0].dt*1000)
        val hour = sdf.format(time)

        searchBackgroundView.setBackgroundResource(locData.fetchBackground(id))
        Glide.with(this).load(locData.fetchIcon(id)).into(searchIconView);

        locData.animateImage(searchIconView)

    }
}

package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.FragmentCurrentBinding
import com.example.weatherapp.model.DataCurrentModel
import com.example.weatherapp.network.ForecastApiCall
import com.example.weatherapp.utlis.LocationData
import com.example.weatherapp.viewmodels.CurrentViewModel
import java.text.SimpleDateFormat
import java.util.*

// Default fragment for city of Zagreb
class CurrentFragment : Fragment() {

    private lateinit var viewModel: CurrentViewModel
    private lateinit var binding: FragmentCurrentBinding
    private lateinit var model: ForecastApiCall
    private var locData = LocationData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCurrentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ForecastApiCall(requireContext())
        viewModel = ViewModelProvider(this)[CurrentViewModel::class.java]
        setLiveDataListeners()
        viewModel.getWeatherInfo(model)
    }

    private fun setLiveDataListeners(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer { forecastData ->
            setWeatherInfo(forecastData)
        })
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setWeatherInfo(data : DataCurrentModel){
        val sdf = SimpleDateFormat("HH:mm:ss, dd.MM.yyyy")
        val currentDate = sdf.format(Date())

        binding.time.text = currentDate
        binding.cityName.text = locData.defaultCityName
        binding.temp.text = "" + locData.kelvinToCelsius(data.temp) + "°C"
        binding.humidity.text = data.humidity.toString() + "%"
        binding.wind.text = data.wind.toString() + "m/s"
        binding.feelsLike.text = "Feels like: " + locData.kelvinToCelsius(data.feels_like) + "°C"
        binding.pressure.text = data.pressure.times(10).toString() + "hPa"
        binding.visibility.text = "" + data.visibility.div(1000).toString() + "km"
        binding.weaterType.text = data.type
        binding.minTemp.text = "" + locData.kelvinToCelsius(data.minTemp) + "°C"
        binding.maxTemp.text = "" + locData.kelvinToCelsius(data.maxTemp) + "°C"

        val id = data.id
        binding.currentConstraint.setBackgroundResource(locData.fetchBackground(id))
        Glide.with(this).load(locData.fetchIcon(id)).into(binding.weatherNow);
        locData.animateImage(binding.weatherNow)

    }
}
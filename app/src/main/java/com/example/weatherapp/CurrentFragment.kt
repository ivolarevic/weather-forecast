package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.FragmentCurrentBinding
import com.example.weatherapp.model.DataCurrentModel
import com.example.weatherapp.network.ForecastApiCall
import com.example.weatherapp.network.NetworkResult
import com.example.weatherapp.utlis.LocationData
import com.example.weatherapp.viewmodels.CurrentViewModel
import java.text.SimpleDateFormat
import java.util.*

// Default fragment for city of Zagreb
class CurrentFragment : Fragment() {

    private lateinit var viewModel: CurrentViewModel
    private lateinit var binding: FragmentCurrentBinding
    private lateinit var model: ForecastApiCall
    private lateinit var constraintLayout : ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private var locData = LocationData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCurrentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ForecastApiCall()
        constraintLayout = binding.currentConstraint
        progressBar = binding.progressBarCurrent
        viewModel = ViewModelProvider(this)[CurrentViewModel::class.java]
        setLiveDataListeners()
        viewModel.getWeatherInfo(model)
    }

    private fun setLiveDataListeners(){
        viewModel.response.observe(viewLifecycleOwner){response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d("success", response.data.toString())
                    viewModel.weatherLiveData.observe(viewLifecycleOwner) { forecastData ->
                        setWeatherInfo(forecastData)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setWeatherInfo(data : DataCurrentModel){
        updateProgressBar()
        val sdf = SimpleDateFormat("HH:mm:ss, dd.MM.yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())

        binding.time.text = currentDate
        binding.cityName.text = getString(R.string.default_city_name, locData.defaultCityName)
        binding.temp.text = "${locData.kelvinToCelsius(data.temp)}°C"
        binding.humidity.text = "${data.humidity}%"
        binding.wind.text = "${data.wind} m/s"
        binding.feelsLike.text = "Feels like: ${locData.kelvinToCelsius(data.feels_like)}°C"
        binding.pressure.text = "${data.pressure.times(10)} hPa"
        binding.visibility.text = "${data.visibility.div(1000)} km"
        binding.weaterType.text = data.type
        binding.minTemp.text = "${locData.kelvinToCelsius(data.minTemp)}°C"
        binding.maxTemp.text = "${locData.kelvinToCelsius(data.maxTemp)}°C"

        val id = data.id
        binding.currentConstraint.setBackgroundResource(locData.fetchBackground(id))
        Glide.with(this).load(locData.fetchIcon(id)).into(binding.weatherNow)
        locData.animateImage(binding.weatherNow)
    }

    private fun updateProgressBar(){
        progressBar.visibility = View.GONE
        constraintLayout.visibility = View.VISIBLE
    }
}
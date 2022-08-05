package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.utlis.LocationData
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.model.data.Forecast
import com.example.weatherapp.network.ForecastApiCall
import com.example.weatherapp.network.NetworkResult
import com.example.weatherapp.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var locData: LocationData
    private lateinit var viewModel: SearchViewModel
    private lateinit var model: ForecastApiCall
    lateinit var cityName : String
    private lateinit var progressBar : ProgressBar
    private lateinit var constraintLayout: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = binding.spinner
        progressBar = binding.progressBarSearch
        constraintLayout = binding.bottomConstraint
        locData = LocationData()
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        ArrayAdapter.createFromResource(
            view.context,
            R.array.cities_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        model = ForecastApiCall()
        fetchSpinnerData(spinner)
        setLiveDataListeners()
    }

    private fun fetchSpinnerData(spinner:Spinner){
        val cities = resources.getStringArray(R.array.cities_array)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                cityName = cities[position]
                viewModel.getWeatherInfo(model,cityName)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                cityName = "Zagreb"
                viewModel.getWeatherInfo(model, cityName)
            }
        }
    }

    private fun setLiveDataListeners(){
        viewModel.response.observe(viewLifecycleOwner){response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d("success", response.data.toString())
                    viewModel.cityName.observe(viewLifecycleOwner) {
                        viewModel.cityName.value = cityName
                    }
                    viewModel.weatherLiveData.observe(viewLifecycleOwner) { forecastData ->
                        updateUI(forecastData)
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

    private fun updateUI(data: Forecast){
        updateProgressBar()
        binding.searchName.text = cityName
        binding.searchDescription.text = data.current.weather[0].description
        binding.searchHumidity.text = "${data.current.humidity} %"
        binding.searchTemp.text = "${locData.kelvinToCelsius(data.current.temp)}°C"
        binding.searchMinTemp.text = "${locData.kelvinToCelsius(data.daily[0].temp.min)}°C"
        binding.searchMaxTemp.text = "${locData.kelvinToCelsius(data.daily[0].temp.max)}°C"
        binding.searchPressure.text = "${data.current.pressure} hPa"
        binding.windSearch.text = "${data.current.wind_speed}m/s"
        binding.visibilitySearch.text= "${data.current.visibility.div(1000)} km"

        val id = data.current.weather[0].id
        binding.searchBackground.setBackgroundResource(locData.fetchBackground(id))
        Glide.with(this).load(locData.fetchIcon(id)).into(binding.searchIcon)
        locData.animateImage(binding.searchIcon)
    }
    private fun updateProgressBar(){
        progressBar.visibility = View.GONE
        constraintLayout.visibility = View.VISIBLE
    }
}

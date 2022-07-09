package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.utlis.LocationData
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.model.data.Forecast
import com.example.weatherapp.network.ForecastApiCall
import com.example.weatherapp.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var locData: LocationData
    private lateinit var viewModel: SearchViewModel
    private lateinit var model: ForecastApiCall
    lateinit var cityName : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = binding.spinner
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

        model = ForecastApiCall(requireContext())
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
        viewModel.cityName.observe(viewLifecycleOwner, Observer{
            viewModel.cityName.value = cityName
        })

        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer { forecastData ->
            updateUI(forecastData)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(data: Forecast){
        binding.searchName.text = cityName
        binding.searchDescription.text = data.current.weather[0].description
        binding.searchHumidity.text = data.current.humidity.toString() + "%"
        binding.searchTemp.text = locData.kelvinToCelsius(data.current.temp).toString() + "°C"
        binding.searchMinTemp.text = locData.kelvinToCelsius(data.daily[0].temp.min).toString() + "°C"
        binding.searchMaxTemp.text = locData.kelvinToCelsius(data.daily[0].temp.max).toString() + "°C"
        binding.searchPressure.text = data.current.pressure.toString() + "hPa"
        binding.windSearch.text = data.current.wind_speed.toString() + "m/s"
        binding.visibilitySearch.text= data.current.visibility.div(1000).toString() + "km"

        val id = data.current.weather[0].id
        binding.searchBackground.setBackgroundResource(locData.fetchBackground(id))
        Glide.with(this).load(locData.fetchIcon(id)).into(binding.searchIcon);
        locData.animateImage(binding.searchIcon)
    }
}

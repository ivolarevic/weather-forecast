package com.example.weatherapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.data.Forecast
import com.example.weatherapp.model.data.Hourly
import com.example.weatherapp.network.ForecastModel
import com.example.weatherapp.network.RequestCompleteListener
import com.example.weatherapp.utlis.LocationData

class HourlyViewModel : ViewModel() {
    private var lat:Float = LocationData().defaultLatitude
    private var lon:Float = LocationData().defaultLongitude
    private var api : String = LocationData().API_KEY

    val weatherLiveData = MutableLiveData<List<Hourly>>()
    val weatherFailureLiveData = MutableLiveData<String>()

    fun getWeatherInfo(model: ForecastModel) {
        model.getWeatherInfo(lat, lon, api, object : RequestCompleteListener<Forecast> {
            override fun onRequestSuccess(data: Forecast) {
                weatherLiveData.value = data.hourly
            }
            override fun onRequestFailed(errorMessage: String) {
                weatherFailureLiveData.postValue(errorMessage)
            }
        })
    }

}
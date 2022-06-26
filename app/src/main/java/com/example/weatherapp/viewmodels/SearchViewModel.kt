package com.example.weatherapp.viewmodels

import android.widget.Spinner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.DataCurrentModel
import com.example.weatherapp.model.data.Forecast
import com.example.weatherapp.network.ForecastModel
import com.example.weatherapp.network.RequestCompleteListener

import com.example.weatherapp.utlis.LocationData

class SearchViewModel : ViewModel() {
    private var api : String = LocationData().API_KEY
    private var locData = LocationData()
    var cityName = MutableLiveData<String>()

    var weatherLiveData = MutableLiveData<Forecast>()
    val weatherFailureLiveData = MutableLiveData<String>()

    fun getWeatherInfo(model: ForecastModel, name : String) {
        locData.getCityCoordinates(name)

        model.getWeatherInfo(locData.selectedCityLatitude, locData.selectedCityLongitude, api, object: RequestCompleteListener<Forecast> {
            override fun onRequestSuccess(data: Forecast) {
                 weatherLiveData.postValue(data)
            }
             override fun onRequestFailed(errorMessage: String) {
                 weatherFailureLiveData.postValue(errorMessage)
             }
        })
    }

}
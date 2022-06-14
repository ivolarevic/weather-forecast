package com.example.weatherapp.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.DataCurrentModel
import com.example.weatherapp.network.ForecastModel
import com.example.weatherapp.network.RequestCompleteListener
import com.example.weatherapp.model.data.Current
import com.example.weatherapp.utlis.LocationData
import com.example.weatherapp.model.data.Forecast

class CurrentViewModel  : ViewModel() {

    private var lat:Float = LocationData().defaultLatitude
    private var lon:Float = LocationData().defaultLongitude
    private var api : String = LocationData().API_KEY

    val weatherLiveData = MutableLiveData<DataCurrentModel>()
    val weatherFailureLiveData = MutableLiveData<String>()

    fun getWeatherInfo(model: ForecastModel) {

        model.getWeatherInfo(lat, lon, api, object: RequestCompleteListener<Forecast> {
            override fun onRequestSuccess(data: Forecast) {
                val weatherData = DataCurrentModel(
                    humidity = data.current.humidity,
                    pressure = data.current.pressure,
                    visibility = data.current.visibility,
                    temp = data.current.temp,
                    feels_like = data.current.feels_like,
                    type = data.current.weather[0].main,
                    wind = data.current.wind_speed,
                    minTemp = data.daily[0].temp.min,
                    maxTemp = data.daily[0].temp.max,
                    id = data.current.weather[0].id,
                )

                weatherLiveData.postValue(weatherData)
            }

            override fun onRequestFailed(errorMessage: String) {
                weatherFailureLiveData.postValue(errorMessage)
            }
        })
    }
}

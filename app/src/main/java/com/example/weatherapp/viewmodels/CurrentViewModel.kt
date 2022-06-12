package com.example.weatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.LocationData
import com.example.weatherapp.repository.Forecast
import com.example.weatherapp.repository.MainRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentViewModel constructor(val repository: MainRepository)  : ViewModel() {

    val weatherList = MutableLiveData<List<Forecast>>()
    var lat:Float = 45.815399f
    var lon:Float = 15.966568f
    var api : String = LocationData().API_KEY
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getWeather() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getCurrentWeatherData(lat, lon, api)
            withContext(Dispatchers.Main) {
                response.enqueue(object : Callback<List<Forecast>> {
                    override fun onResponse(call: Call<List<Forecast>>, response: Response<List<Forecast>>) {
                        weatherList.postValue(response.body())
                        Log.d("success", weatherList.toString())
                    }
                    override fun onFailure(call: Call<List<Forecast>>, t: Throwable) {
                        Log.d("failed", t.toString())
                    }
                })
            }
        }
    }
    private fun onError(message: String) {
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

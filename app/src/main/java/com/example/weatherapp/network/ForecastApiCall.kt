package com.example.weatherapp.network

import com.example.weatherapp.model.data.Forecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastApiCall: ForecastModel {
    override fun getWeatherInfo(latitude: Float, longitude: Float, apiKey: String, callback: RequestCompleteListener<Forecast>){
        val interfaceAPI: InterfaceAPI = InterfaceAPI.create()
        val call: Call<Forecast> = interfaceAPI.getCurrentWeatherData(latitude, longitude, apiKey)

        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                     callback.onRequestSuccess(response.body()!!, response.code().toString())
                }else{
                    callback.onRequestFailed(response.message())
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!)
            }
        })
    }
}


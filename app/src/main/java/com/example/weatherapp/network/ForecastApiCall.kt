package com.example.weatherapp.network

import android.content.Context
import com.example.weatherapp.model.data.Forecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastApiCall(private val context: Context): ForecastModel {
    override fun getWeatherInfo(latitude: Float, longitude: Float, apiKey: String, callback: RequestCompleteListener<Forecast>) {

        val interfaceAPI: InterfaceAPI = InterfaceAPI.create()
        val call: Call<Forecast> = interfaceAPI.getCurrentWeatherData(latitude, longitude, apiKey)

        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    callback.onRequestSuccess(response.body()!!)
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


package com.example.weatherapp.network

import com.example.weatherapp.model.data.Forecast

interface ForecastModel {
    fun getWeatherInfo(latitude: Float, longitude:Float, apiKey:String, callback: RequestCompleteListener<Forecast>)
}
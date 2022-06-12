package com.example.weatherapp.repository

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getCurrentWeatherData(lat: Float, lon: Float, apiKey: String) =
        retrofitService.getCurrentWeatherData(lat,lon,apiKey)

}
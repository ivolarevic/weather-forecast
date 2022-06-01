package com.example.weatherapp.api

data class DataHourlyModel (
    var hour:String,
    var weatherIconDaily:Int,
    var hourlyDescription: String,
    var hourlyTemp: String,
)
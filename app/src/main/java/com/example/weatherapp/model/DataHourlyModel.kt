package com.example.weatherapp.model

data class DataHourlyModel (
    var hour:String,
    var icon:Int,
    var background:Int,
    var hourlyDescription: String,
    var hourlyTemp: String,
)
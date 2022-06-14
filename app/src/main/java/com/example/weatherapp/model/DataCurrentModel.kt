package com.example.weatherapp.model

data class DataCurrentModel (
    val temp : Float,
    val feels_like: Float,
    val type:String,
    val humidity: Int,
    val wind: Float,
    val minTemp: Float,
    val maxTemp: Float,
    val pressure: Float,
    val visibility : Float,
    val id : Int,
)
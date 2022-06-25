package com.example.weatherapp.model

data class DataWeeklyModel(
    val description: String,
    val humidity: Int,
    val pressure: Float,
    val temp: Float,
    //val type: String,
    val wind: Float,
    val minTemp: Float,
    val maxTemp: Float,
    val id: Int,
    val rain: Float,
    val dt: Long,
)


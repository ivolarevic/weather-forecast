package com.example.weatherapp.model

data class DataDailyModel(
    val description: String,
    val temp: Float,
    val humidity: Float,
    val wind: Float,
    val pressure: Float,
    val id: Int,
    val dt: Long,
)
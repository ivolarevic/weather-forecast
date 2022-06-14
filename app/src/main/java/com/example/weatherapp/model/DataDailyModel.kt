package com.example.weatherapp.model

data class DataDailyModel (
    val description: String,
    val day: String,
    val icon: Int,
    val minTemp: String,
    val maxTemp: String,
    val rain: Float,
    val humidity: Int,
    val wind: Float,
    val pressure: Float,
    val background: Int,
    )
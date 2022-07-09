package com.example.weatherapp.model

data class WeeklyListModel (
    val description: String,
    val humidity: Int,
    val pressure: Float,
    val temp: Float,
    val wind: Float,
    val minTemp: String,
    val maxTemp: String,
    val id: Int,
    val rain: Float,
    val dayOfWeek: String,
    val background: Int,
    val icon: Int,

)
package com.example.weatherapp.model.data

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("lat") val lat:Float,
    @SerializedName("lon") val lon:Float,
    @SerializedName("timezone") val timezone:String,
    @SerializedName("timezone_offset") val timezone_offset:Float,
    @SerializedName("current") val current: Current,
    @SerializedName("minutely") val minutely:List<Minutely>,
    @SerializedName("hourly") val hourly:List<Hourly>,
    @SerializedName("daily") val daily:List<Daily>,
)

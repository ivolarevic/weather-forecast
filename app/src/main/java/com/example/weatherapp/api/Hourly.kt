package com.example.weatherapp.api

import com.google.gson.annotations.SerializedName

data class Hourly (
    @SerializedName("dt") val dt:Long,
    @SerializedName("temp") val temp:Float,
    @SerializedName("feels_like") val feels_like:Float,
    @SerializedName("pressure") val pressure:Float,
    @SerializedName("humidity") val humidity:Float,
    @SerializedName("dew_point") val dew_point:Float,
    @SerializedName("uvi") val uvi:Float,
    @SerializedName("clouds") val clouds:Float,
    @SerializedName("visibility") val visibility:Float,
    @SerializedName("wind_speed") val wind_speed:Float,
    @SerializedName("wind_deq") val wind_deq:Float,
    @SerializedName("weather") val weather:List<Weather>,
    @SerializedName("rain") val rain:Rain,


    )


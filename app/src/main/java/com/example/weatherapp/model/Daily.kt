package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Daily (
    @SerializedName("dt") val dt:Long,
    @SerializedName("sunrise") val sunrise:Long,
    @SerializedName("sunset") val sunset:Long,
    @SerializedName("moonrise") val moonrise:Long,
    @SerializedName("moonset") val moonset:Long,
    @SerializedName("moon_phase") val moon_phase:Float,
    @SerializedName("temp") val temp:Temp,
    @SerializedName("feels_like") val feels_like:FeelsLike,
    @SerializedName("pressure") val pressure:Float,
    @SerializedName("humidity") val humidity:Int,
    @SerializedName("dew_point") val dew_point:Double,
    @SerializedName("wind_speed") val wind_speed:Float,
    @SerializedName("wind_deg") val wind_deg:Float,
    @SerializedName("weather") val weather:List<Weather>,
    @SerializedName("clouds") val clouds:Float,
    @SerializedName("pop") val pop:Float,
    @SerializedName("rain") val rain:Float,
    @SerializedName("uvi") val uvi:Float,
)




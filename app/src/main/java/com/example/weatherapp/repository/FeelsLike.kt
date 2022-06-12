package com.example.weatherapp.repository

import com.google.gson.annotations.SerializedName

data class FeelsLike(
    @SerializedName("day") val day:Float,
    @SerializedName("night") val night:Float,
    @SerializedName("eve") val eve:Float,
    @SerializedName("morn") val morn:Float,
)

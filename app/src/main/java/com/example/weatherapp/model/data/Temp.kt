package com.example.weatherapp.model.data

import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("day") val day:Float,
    @SerializedName("min") val min:Float,
    @SerializedName("max") val max:Float,
    @SerializedName("night") val night:Float,
    @SerializedName("eve") val eve:Float,
    @SerializedName("morn") val morn:Float,
)


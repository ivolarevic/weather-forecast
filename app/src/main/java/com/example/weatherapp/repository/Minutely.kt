package com.example.weatherapp.repository

import com.google.gson.annotations.SerializedName

 class Minutely (
    @SerializedName("dt") val dt:Long,
    @SerializedName("precipitation") val precipitation:Float,
)
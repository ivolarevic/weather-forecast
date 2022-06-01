package com.example.weatherapp.api

import com.google.gson.annotations.SerializedName

 class Minutely (
    @SerializedName("dt") val dt:Long,
    @SerializedName("precipitation") val precipitation:Float,
)
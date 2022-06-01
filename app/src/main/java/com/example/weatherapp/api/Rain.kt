package com.example.weatherapp.api

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h") val h1:Float,
)

package com.example.weatherapp.repository

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h") val h1:Float,
)

package com.example.weatherapp.network

import com.example.weatherapp.model.data.Forecast

interface RequestCompleteListener<T> {
    fun onRequestSuccess(data: Forecast)
    fun onRequestFailed(errorMessage: String)
}
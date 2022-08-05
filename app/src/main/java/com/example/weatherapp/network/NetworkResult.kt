package com.example.weatherapp.network

sealed class NetworkResult<T>(val data: Any? = null, val message: String? = null){
    class Success<T>(message: String?) : NetworkResult<T>(message)
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

}
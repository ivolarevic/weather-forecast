package com.example.weatherapp.network

import com.example.weatherapp.model.data.Forecast
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceAPI {

    @GET("/data/2.5/onecall")
    fun getCurrentWeatherData(
        @Query("lat")  latitude:Float,
        @Query("lon")  longitude:Float,
        @Query("appid")  APPID:String,
    ): Call<Forecast>

    companion object{
        var BASE_URL="https://api.openweathermap.org"

        fun create() : InterfaceAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(InterfaceAPI::class.java)
        }
    }
}
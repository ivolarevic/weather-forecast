package com.example.weatherapp.repository

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

public interface RetrofitService {

    @GET("/data/2.5/onecall")
    suspend fun getCurrentWeatherData(
        @Query("lat")  latitude:Float,
        @Query("lon")  longitude:Float,
        @Query("appid")  APPID:String,
    ): Call<List<Forecast>>

    companion object{
        var BASE_URL="https://api.openweathermap.org"
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}


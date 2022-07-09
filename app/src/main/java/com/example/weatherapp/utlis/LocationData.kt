package com.example.weatherapp.utlis

import android.util.Pair
import android.widget.ImageView
import com.example.weatherapp.R
import java.math.RoundingMode

open class LocationData() {

    val API_KEY = "b57f02b119fe88ea62242ec5fa20cac2"
    var idHourlyIcon: Int = 0
    var idCurrentBack: Int = 0
    // Zagreb
    var defaultCityName = "Zagreb"
    var defaultLatitude:Float = 45.815399f
    var defaultLongitude:Float = 15.966568f

    var selectedCityLatitude:Float = 0.0f
    var selectedCityLongitude:Float = 0.0f


    fun setDefaultLatitude() : Float {
        return defaultLatitude
    }
    fun setDefaultLongitude() : Float {
        return defaultLongitude
    }

    fun apiKey() : String{
        return "d32c530968b46cca52ed08edcf0d6a93"
    }

    var cityCoordinates = mapOf("Amsterdam" to Pair(52.379189f, 4.899431f),
        "Athens" to Pair(37.983810f, 23.727539f),
        "Ankara" to Pair(39.925533f, 32.866287f),
        "Budapest" to Pair(47.497913f, 19.040236f),
        "Berlin" to Pair(52.520008f, 13.404954f),
        "Bern" to Pair(46.947456f, 7.451123f),
        "Bratislava" to Pair(48.148598f, 17.107748f),
        "City of Brussels" to Pair(50.849996f, 4.349998f),
        "Bucharest" to Pair(44.439663f, 26.096306f),
        "Copenhagen" to Pair(55.676098f, 12.568337f),
        "Dublin" to Pair(53.350140f, -6.266155f),
        "Gibraltar" to Pair(36.144740f, -5.352570f),
        "Helsinki" to Pair(60.192059f, 24.945831f),
        "Kiev" to Pair(50.454660f, 30.523800f),
        "Lisbon" to Pair(38.736946f, -9.142685f),
        "Ljubljana" to Pair(46.056946f, 14.505751f),
        "London" to Pair(51.509865f, -0.118092f),
    )


    fun getCityCoordinates(cityName:String){
        var tmp: Pair<Float, Float>? = cityCoordinates[cityName]
        if (tmp != null) {
            selectedCityLatitude = tmp.first
            selectedCityLongitude = tmp.second
        }
    }

    fun kelvinToCelsius(temp: Float): Any? {
        return (temp - 273.15).toInt()
    }

    fun fetchIcon(id: Int) : Int {
        if(id in 200..232){
            idHourlyIcon = R.drawable.thunderstorm_icon
        }
        else if(id in 500..531 || id in 300..321){
            idHourlyIcon = R.drawable.rain_transparent
        }
        else if(id in 600..622){
            idHourlyIcon = R.drawable.snow_transparent
        }
        else if(id == 800){
            idHourlyIcon = R.drawable.sun_trasparent
        }
        else if(id in 801..804){
            idHourlyIcon = R.drawable.clouds_transparent
        }
        return idHourlyIcon
    }

    fun fetchBackground(id: Int) : Int {
        if(id in 200..232){
            idCurrentBack = R.drawable.thunderstorm_background
        }
        else if(id in 500..531 || id in 300..321){
            idCurrentBack = R.drawable.rain_background
        }
        else if(id in 600..622){
            idCurrentBack = R.drawable.snow_background
        }
        else if(id == 800){
            idCurrentBack = R.drawable.clear_background
        }
        else if(id in 801..804){
            idCurrentBack = R.drawable.snow_background
        }
        return idCurrentBack
    }

    fun fetchNightIcon(id: Int) : Int{
        if(id == 800){
            idHourlyIcon = R.drawable.moon_clear
        }
        else{
            idHourlyIcon = R.drawable.moon_transaprent
        }
        return idHourlyIcon
    }

    fun fetchNightBackground() : Int{
        idCurrentBack = R.drawable.snow_background
        return idCurrentBack
    }


    fun animateImage(image: ImageView?){
        image!!.animate().apply {
            duration = 1000
            rotationYBy(360f)
        }.start()
    }
}

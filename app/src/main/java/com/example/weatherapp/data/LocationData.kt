package com.example.weatherapp.data

class LocationData {

    private val PATH_URL = "/data/2.5/onecall"
    private val BASE_URL = "https://api.openweathermap.org"
    private val API_KEY = "d32c530968b46cca52ed08edcf0d6a93"

    // Zagreb
    public val defaultLatitude = 45.815399f
    public val defaultLongitude = 15.966568f

    public fun getZagrebLatitude(): Float?{
        return defaultLatitude
    }

    public fun getZagrebLongitude(): Float?{
        return defaultLongitude
    }


}
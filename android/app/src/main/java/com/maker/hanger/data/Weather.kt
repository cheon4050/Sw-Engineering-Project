package com.maker.hanger.data

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("probability") var probability: Double,
    @SerializedName("humidity") var humidity: Double,
    @SerializedName("state") var state: String,
    @SerializedName("present") var present: Double,
)

data class WeatherResponse(
    @SerializedName("status") var status: Int,
    @SerializedName("message") var message: String,
    @SerializedName("weather") var weather: Weather
)
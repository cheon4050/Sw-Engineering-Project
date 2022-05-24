package com.maker.hanger.data

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("precipitationProbability") var precipitationProbability: Int,
    @SerializedName("humidity") var humidity: Int,
    @SerializedName("state") var state: String,
    @SerializedName("presentTemperature") var presentTemperature: Float,
    @SerializedName("highestTemperature") var highestTemperature: Float,
    @SerializedName("lowestTemperature") var lowestTemperature: Float
)
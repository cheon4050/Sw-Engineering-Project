package com.maker.hanger.connection

import com.maker.hanger.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET

interface WeatherRetrofitInterface {
    @GET("/weather")
    fun getWeather(): Call<WeatherResponse>
}
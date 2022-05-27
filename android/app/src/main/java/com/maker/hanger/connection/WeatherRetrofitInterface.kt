package com.maker.hanger.connection

import com.maker.hanger.data.Weather
import retrofit2.Call
import retrofit2.http.GET

interface WeatherRetrofitInterface {
    @GET("/weather")
    fun get(): Call<Weather>
}
package com.maker.hanger.connection

import android.util.Log
import com.maker.hanger.data.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherService {
    private lateinit var weatherView: WeatherView

    fun setWeatherView(weatherView: WeatherView) {
        this.weatherView = weatherView
    }

    fun getWeather() {
        val weatherService = getRetrofit().create(WeatherRetrofitInterface::class.java)
        weatherService.getWeather().enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                Log.d("WEATHER/SUCCESS", response.toString())
                if (response.isSuccessful) {
                    val resp: WeatherResponse = response.body()!!
                    weatherView.onGetWeatherSuccess(resp.weather)
                } else {
                    weatherView.onGetWeatherFailure(response.code())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("WEATHER/FAILURE", t.message.toString())
            }
        })
    }
}
package com.maker.hanger.connection

import com.maker.hanger.data.Weather

interface WeatherView {
    fun onGetWeatherSuccess(weather: Weather)
    fun onGetWeatherFailure()
}
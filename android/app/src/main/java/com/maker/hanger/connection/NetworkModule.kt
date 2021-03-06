package com.maker.hanger.connection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http:10.0.2.2:8080"

fun getRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
}
package com.maker.hanger.connection

import com.maker.hanger.data.User
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("/auth")
    fun signUp(@Body user: User): Call<User>

    @GET("/auth/login")
    fun login(@Body user: User): Call<User>

    @GET("/auth/password")
    fun find(@Body user: User): Call<User>

    @PUT("/auth/user")
    fun modify(@Body user: User): Call<User>

    @DELETE("/auth/user")
    fun delete(@Body userIdx: Int): Call<User>
}
package com.maker.hanger.connection

import com.maker.hanger.data.*
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("/auth")
    fun signUp(
        @Body userSignUpRequest: UserSignUpRequest
    ): Call<UserResponse>

    @GET("/auth/check")
    fun idCheck(
        @Query("userId") userId: String
    ): Call<UserResponse>

    @POST("/auth/login")
    fun login(
        @Body user: User
    ): Call<UserLoginResponse>

    @POST("/auth/password")
    fun find(
        @Body userFindPasswordRequest: UserFindPasswordRequest
    ): Call<UserFindPasswordResponse>

    @PUT("/auth/user")
    fun update(
        @Header("userToken") userToken: String?,
        @Body user: User
    ): Call<UserResponse>

    @DELETE("/auth/user")
    fun delete(
        @Header("userToken") userToken: String?
    ): Call<UserResponse>
}
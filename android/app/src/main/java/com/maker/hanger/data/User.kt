package com.maker.hanger.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName(value = "userToken") var userToken: String,
    @SerializedName(value = "userId") var userId: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "birth") var birth: Int
)

data class UserSignUpRequest(
    @SerializedName(value = "userId") var userId: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "birth") var birth: Int
)

data class UserLoginRequest(
    @SerializedName(value = "userId") var userId: String,
    @SerializedName(value = "password") var password: String
)

data class UserFindPasswordRequest(
    @SerializedName(value = "userId") var userId: String,
    @SerializedName(value = "birth") var birth: Int
)

data class UserResponse(
    @SerializedName(value = "status") var status: Int,
    @SerializedName(value = "message") var message: String,
)

data class UserLoginResponse(
    @SerializedName(value = "status") var status: Int,
    @SerializedName(value = "message") var message: String,
    @SerializedName(value = "userToken") var userToken: String,
)

data class UserFindPasswordResponse(
    @SerializedName(value = "status") var status: Int,
    @SerializedName(value = "message") var message: String,
    @SerializedName(value = "password") var password: String
)
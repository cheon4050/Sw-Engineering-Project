package com.maker.hanger.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName(value = "userIdx") var userIdx: Int,
    @SerializedName(value = "userId") var userId: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "birth") var birth: Int
)

package com.maker.hanger.data

import com.google.gson.annotations.SerializedName

data class Style(
    @SerializedName("outer") var outer: String?,
    @SerializedName("top") var top: String,
    @SerializedName("bottom") var bottom: String,
    @SerializedName("shoes") var shoes: String
)
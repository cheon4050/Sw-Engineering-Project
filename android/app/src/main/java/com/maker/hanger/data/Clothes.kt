package com.maker.hanger.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clothes(
    @SerializedName(value = "clothesIdx") var clothesIdx: Int,
    @SerializedName(value = "clothesImage") var clothesImage: String,
    @SerializedName(value = "date") var date: String,
    @SerializedName(value = "session") var session: String,
    @SerializedName(value = "kind") var kind: ArrayList<String>,
    @SerializedName(value = "washingMethod") var washingMethod: ArrayList<Int>,
    @SerializedName(value = "size") var size: Char,
    @SerializedName(value = "bookmark") var bookmark: Boolean
) : Serializable

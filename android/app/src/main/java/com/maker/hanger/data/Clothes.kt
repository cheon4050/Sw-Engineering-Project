package com.maker.hanger.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clothes(
    @SerializedName(value = "clothesIdx") var clothesIdx: Int,
    @SerializedName(value = "clothesImage") var clothesImage: String,
    @SerializedName(value = "date") var date: String,
    @SerializedName(value = "season") var season: String, // 추후에 ArrayList<String>으로 변경 예정
    @SerializedName(value = "kind") var kind: ArrayList<String>,
    @SerializedName(value = "washingMethod") var washingMethod: ArrayList<Int>,
    @SerializedName(value = "size") var size: Char,
    @SerializedName(value = "bookmark") var bookmark: Boolean
) : Serializable

data class ClothesResponse(
    var state: Int,
    var message: String,
    var clothes: Clothes
)

data class ClothesRequest(
    var data: String,
    var season: ArrayList<String>,
    var kind: ArrayList<String>,
    var washingMethod: ArrayList<Int>
)
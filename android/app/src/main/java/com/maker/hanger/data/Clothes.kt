package com.maker.hanger.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clothes(
    @SerializedName(value = "clothesIdx") var clothesIdx: Int,
    @SerializedName(value = "clothesImage") var clothesImage: String,
    @SerializedName(value = "date") var date: String, // 추후에 Date로 변경 예정
    @SerializedName(value = "season") var season: String, // 추후에 ArrayList<String>으로 변경 예정
    @SerializedName(value = "kind") var kind: ArrayList<String>,
    @SerializedName(value = "washingMethod") var washingMethod: ArrayList<Int>,
    @SerializedName(value = "size") var size: Char,
    @SerializedName(value = "bookmark") var bookmark: Boolean
) : Serializable

data class ClothesRequest(
    @SerializedName(value = "data") var data: String, // 추후에 Date로 변경 예정
    @SerializedName(value = "season") var season: ArrayList<String>,
    @SerializedName(value = "kind") var kind: ArrayList<String>,
    @SerializedName(value = "washingMethod") var washingMethod: ArrayList<Int>,
    @SerializedName(value = "size") var size: Char
)

data class ClothesResponse(
    @SerializedName(value = "status") var status: Int,
    @SerializedName(value = "message") var message: String
)

data class ClothesSearchResponse(
    @SerializedName(value = "status") var status: Int,
    @SerializedName(value = "message") var message: String,
    @SerializedName(value = "clothes") var clothes: ArrayList<Clothes>
)

data class ClothesRecommendResponse(
    @SerializedName(value = "status") var status: Int,
    @SerializedName(value = "message") var message: String,
    @SerializedName(value = "status") var style: ArrayList<Style>
)
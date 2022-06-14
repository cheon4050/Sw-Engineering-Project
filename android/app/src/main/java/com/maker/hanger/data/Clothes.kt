package com.maker.hanger.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clothes(
    @SerializedName(value = "clothesIdx") var clothesIdx: Int,
    @SerializedName(value = "clothesImageUrl") var clothesImageUrl: String,
    @SerializedName(value = "date") var date: String,
    @SerializedName(value = "season") var season: ArrayList<String>,
    @SerializedName(value = "kind") var kind: ArrayList<String>,
    @SerializedName(value = "washingMethod") var washingMethod: ArrayList<Int>,
    @SerializedName(value = "size") var size: Char,
    @SerializedName(value = "bookmark") var bookmark: Boolean
) : Serializable

data class ClothesRequest(
    @SerializedName(value = "clothesImageUrl") var clothesImageUrl: String,
    @SerializedName(value = "date") var date: String,
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

data class ClothesSearchInfoResponse(
    @SerializedName(value = "status") var status: Int,
    @SerializedName(value = "message") var message: String,
    @SerializedName(value = "clothes") var clothes: Clothes
)

data class ClothesRecommendResponse(
    @SerializedName(value = "status") var status: Int,
    @SerializedName(value = "message") var message: String,
    @SerializedName(value = "clothesImageUrl") var clothesImageUrl: ArrayList<String>
)
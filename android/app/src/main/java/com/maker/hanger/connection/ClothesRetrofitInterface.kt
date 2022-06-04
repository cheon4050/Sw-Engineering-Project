package com.maker.hanger.connection

import com.maker.hanger.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ClothesRetrofitInterface {
    @Multipart
    @POST("/clothes")
    fun add(
        @Header("userToken") userToken: String?,
        @Part clothesImage: MultipartBody.Part,
        @Part("clothes") clothes: RequestBody
    ): Call<ClothesResponse>

    @GET("/clothes")
    fun search(
        @Header("userToken") userToken: String?,
        @Query("season") season: ArrayList<String>,
        @Query("kind") kind: ArrayList<String>,
        @Query("bookmark") bookmark: Boolean
    ): Call<ClothesSearchResponse>

    @DELETE("/clothes")
    fun delete(
        @Header("userToken") userToken: String?,
        @Query("clothesIdx") clothesIdx: Int
    ): Call<ClothesResponse>

    @Multipart
    @PUT("/clothes")
    fun update(
        @Header("userToken") userToken: String?,
        @Part clothesImage: MultipartBody.Part,
        @Part("clothes") clothes: RequestBody,
        @Query("clothesIdx") clothesIdx: Int
    ): Call<ClothesResponse>

    @GET("/clothes/info")
    fun searchInfo(
        @Header("userToken") userToken: String?,
        @Query("clothesIdx") clothesIdx: Int
    ): Call<ClothesSearchInfoResponse>

    @POST("/clothes/bookmark")
    fun bookmark(
        @Header("userToken") userToken: String?,
        @Query("clothesIdx") clothesIdx: Int,
        @Query("bookmark") bookmark: Boolean,
    ): Call<ClothesResponse>

    @GET("/clothes/recommend")
    fun recommend(
        @Header("userToken") userToken: String?
    ): Call<ClothesRecommendResponse>
}
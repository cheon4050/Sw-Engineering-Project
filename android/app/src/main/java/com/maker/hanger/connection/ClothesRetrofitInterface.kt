package com.maker.hanger.connection

import com.maker.hanger.data.Clothes
import retrofit2.Call
import retrofit2.http.*

interface ClothesRetrofitInterface {
    @POST("/clothes")
    fun add(@Header("userIdx") userIdx: Int, @Body clothes: Clothes)

    @GET("/clothes")
    fun search(@Query("session") session: String?, @Query("kind") kind: String?,
               @Query("bookmark") bookmark: Boolean?, @Header("userIdx") userIdx: Int): Call<ArrayList<Clothes>>

    @DELETE("/clothes")
    fun delete(@Query("clothesIdx") clothesIdx: Int, @Header("userIdx") userIdx: Int)

    @PUT("/clothes")
    fun modify(@Query("clothesIdx") clothesIdx: Int, @Header("userIdx") userIdx: Int, @Body clothes: Clothes)

    @GET("/clothes/Info")
    fun searchInfo(@Query("clothesIdx") clothesIdx: Int, @Header("userIdx") userIdx: Int): Call<Clothes>

    @POST("/clothes/Bookmark")
    fun bookmark(@Query("clothesIdx") clothesIdx: Int, @Header("userIdx") userIdx: Int)
}
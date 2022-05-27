package com.maker.hanger.connection

import com.maker.hanger.data.Clothes
import com.maker.hanger.data.Style
import retrofit2.Call
import retrofit2.http.*

interface ClothesRetrofitInterface {
    @POST("/clothes")
    fun add(@Header("userToken") userToken: String?, @Body clothes: Clothes)

    @GET("/clothes")
    fun search(@Query("session") session: String?, @Query("kind") kind: String?,
               @Query("bookmark") bookmark: Boolean?, @Header("userToken") userToken: String?): Call<ArrayList<Clothes>>

    @DELETE("/clothes")
    fun delete(@Query("clothesIdx") clothesIdx: Int, @Header("userToken") userToken: String?)

    @PUT("/clothes")
    fun modify(@Query("clothesIdx") clothesIdx: Int, @Header("userToken") userToken: String?, @Body clothes: Clothes)

    @GET("/clothes/Info")
    fun searchInfo(@Query("clothesIdx") clothesIdx: Int, @Header("userToken") userToken: String?): Call<Clothes>

    @POST("/clothes/Bookmark")
    fun bookmark(@Query("clothesIdx") clothesIdx: Int, @Header("userToken") userToken: String?)

    @GET("/clothes/Recommend")
    fun recommend(@Header("userToken") userToken: String?): Call<ArrayList<Style>>
}
package com.maker.hanger.connection

import com.maker.hanger.data.Clothes
import com.maker.hanger.data.ClothesResponse
import com.maker.hanger.data.Style
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
    fun search(@Query("season") season: String?, @Query("kind") kind: String?,
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
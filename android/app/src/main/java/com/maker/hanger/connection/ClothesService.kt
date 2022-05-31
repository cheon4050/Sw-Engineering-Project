package com.maker.hanger.connection

import android.util.Log
import com.maker.hanger.data.Clothes
import com.maker.hanger.data.ClothesResponse
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClothesService {
    private lateinit var registerView: RegisterView

    fun setRegisterView(registerView: RegisterView) {
        this.registerView = registerView
    }

    fun add(userToken: String?, clothesImage: MultipartBody.Part, clothes: RequestBody) {
        val clothesService = getRetrofit().create(ClothesRetrofitInterface::class.java)
        clothesService.add(userToken, clothesImage, clothes).enqueue(object: Callback<ClothesResponse> {
            override fun onResponse(call: Call<ClothesResponse>, response: Response<ClothesResponse>) {
                Log.d("ADD/SUCCESS", response.toString())
                val resp: ClothesResponse = response.body()!!
                when (resp.state) {
                    200 -> registerView.onRegisterSuccess()
                    else -> registerView.onRegisterFailure()
                }
            }

            override fun onFailure(call: Call<ClothesResponse>, t: Throwable) {
                Log.d("ADD/FAILURE", t.message.toString())
            }
        })
    }
}
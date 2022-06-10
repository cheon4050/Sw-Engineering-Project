package com.maker.hanger.connection

import android.util.Log
import com.maker.hanger.data.UserLoginRequest
import com.maker.hanger.data.UserLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var loginView: LoginView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun login(userLoginRequest: UserLoginRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(userLoginRequest).enqueue(object: Callback<UserLoginResponse> {
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())
                val resp: UserLoginResponse = response.body()!!
                when (resp.status) {
                    200 -> loginView.onLoginSuccess(resp.userToken)
                    else -> loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
    }
}
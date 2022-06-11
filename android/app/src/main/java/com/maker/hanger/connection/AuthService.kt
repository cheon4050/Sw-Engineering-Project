package com.maker.hanger.connection

import android.util.Log
import com.maker.hanger.data.UserLoginRequest
import com.maker.hanger.data.UserLoginResponse
import com.maker.hanger.data.UserResponse
import com.maker.hanger.data.UserSignUpRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signUp(userSignUpRequest: UserSignUpRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(userSignUpRequest).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: UserResponse = response.body()!!
                when (resp.status) {
                    200 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })
    }

    fun idCheck(userId: String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.idCheck(userId).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("IDCHECK/SUCCESS", response.toString())
                val resp: UserResponse = response.body()!!
                when (resp.status) {
                    200 -> signUpView.onIdCheckSuccess()
                    else -> signUpView.onIdCheckFailure()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("IDCHECK/FAILURE", t.message.toString())
            }
        })
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
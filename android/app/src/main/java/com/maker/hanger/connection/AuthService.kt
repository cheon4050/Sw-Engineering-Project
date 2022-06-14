package com.maker.hanger.connection

import android.util.Log
import com.maker.hanger.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var findPasswordView: FindPasswordView
    private lateinit var modifyUserView: ModifyUserView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun setFindPasswordView(findPasswordView: FindPasswordView) {
        this.findPasswordView = findPasswordView
    }

    fun setModifyUserView(modifyUserView: ModifyUserView) {
        this.modifyUserView = modifyUserView
    }

    fun signUp(userSignUpRequest: UserSignUpRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(userSignUpRequest).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                if (response.code() == 400) {
                    signUpView.onSignUpFailure()
                } else {
                    signUpView.onSignUpSuccess()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })
    }

    fun idCheck(userId: String, view: String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.idCheck(userId).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("IDCHECK/SUCCESS", response.toString())
                if (view == "signUp") {
                    if (response.code() == 200) {
                        signUpView.onIdCheckFailure()
                    } else {
                        signUpView.onIdCheckSuccess()
                    }
                } else {
                    if (response.code() == 200) {
                        modifyUserView.onIdCheckFailure()
                    } else {
                        modifyUserView.onIdCheckSuccess()
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("IDCHECK/FAILURE", t.message.toString())
            }
        })
    }

    fun login(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object: Callback<UserLoginResponse> {
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())
                if (response.code() == 400) {
                    loginView.onLoginFailure()
                } else {
                    val resp: UserLoginResponse = response.body()!!
                    loginView.onLoginSuccess(resp.userToken)
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
    }

    fun find(userFindPasswordRequest: UserFindPasswordRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.find(userFindPasswordRequest).enqueue(object: Callback<UserFindPasswordResponse>{
            override fun onResponse(call: Call<UserFindPasswordResponse>, response: Response<UserFindPasswordResponse>) {
                Log.d("FIND/SUCCESS", response.toString())
                if (response.code() == 400) {
                    findPasswordView.onFindPasswordFailure()
                } else {
                    val resp: UserFindPasswordResponse = response.body()!!
                    findPasswordView.onFindPasswordSuccess(resp.password)
                }
            }

            override fun onFailure(call: Call<UserFindPasswordResponse>, t: Throwable) {
                Log.d("FIND/FAILURE", t.message.toString())
            }
        })
    }

    fun update(userToken: String?, user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.update(userToken, user).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("UPDATE/SUCCESS", response.toString())
                if (response.code() == 400) {
                    modifyUserView.onUpdateFailure()
                } else {
                    modifyUserView.onUpdateSuccess()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("UPDATE/FAILURE", t.message.toString())
            }
        })
    }

    fun delete(userToken: String?) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.delete(userToken).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("DELETE/SUCCESS", response.toString())
                if (response.code() == 400) {
                    modifyUserView.onWithdrawalFailure()
                } else {
                    modifyUserView.onWithdrawalSuccess()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("DELETE/FAILURE", t.message.toString())
            }
        })
    }
}
package com.maker.hanger.connection

interface LoginView {
    fun onLoginSuccess(userToken: String)
    fun onLoginFailure()
}
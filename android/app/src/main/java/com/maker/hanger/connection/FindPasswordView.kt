package com.maker.hanger.connection

interface FindPasswordView {
    fun onFindPasswordSuccess(password: String)
    fun onFindPasswordFailure()
}
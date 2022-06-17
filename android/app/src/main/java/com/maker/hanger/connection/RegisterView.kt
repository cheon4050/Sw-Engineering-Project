package com.maker.hanger.connection

interface RegisterView {
    fun onRegisterSuccess()
    fun onRegisterFailure(status: Int)
}
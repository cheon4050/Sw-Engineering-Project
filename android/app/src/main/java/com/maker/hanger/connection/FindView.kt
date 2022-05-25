package com.maker.hanger.connection

interface FindView {
    fun onFindSuccess(password: String)
    fun onFindFailure()
}
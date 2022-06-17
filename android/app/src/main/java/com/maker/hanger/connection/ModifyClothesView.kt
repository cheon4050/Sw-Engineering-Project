package com.maker.hanger.connection

interface ModifyClothesView {
    fun onUpdateSuccess()
    fun onUpdateFailure(status: Int)
}
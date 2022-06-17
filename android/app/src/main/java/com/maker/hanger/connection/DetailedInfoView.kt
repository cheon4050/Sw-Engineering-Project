package com.maker.hanger.connection

import com.maker.hanger.data.Clothes

interface DetailedInfoView {
    fun onSearchInfoSuccess(clothes: Clothes)
    fun onSearchInfoFailure(status: Int)
    fun onDeleteSuccess()
    fun onDeleteFailure(status: Int)
}
package com.maker.hanger.connection

import com.maker.hanger.data.Clothes

interface SearchView {
    fun onSearchSuccess(clothes: ArrayList<Clothes>)
    fun onSearchFailure(status: Int)
}
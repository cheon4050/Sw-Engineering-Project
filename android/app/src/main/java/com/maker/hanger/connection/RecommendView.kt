package com.maker.hanger.connection

interface RecommendView {
    fun onRecommendSuccess(clothesImageUrl: ArrayList<String>)
    fun onRecommendFailure()
}
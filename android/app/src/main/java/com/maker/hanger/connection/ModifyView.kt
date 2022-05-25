package com.maker.hanger.connection

interface ModifyView {
    fun onModifySuccess()
    fun onModifyFailure()
    fun onWithdrawalSuccess()
    fun onWithdrawalFailure()
}
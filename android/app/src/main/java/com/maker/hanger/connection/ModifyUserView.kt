package com.maker.hanger.connection

interface ModifyUserView {
    fun onUpdateSuccess()
    fun onUpdateFailure()
    fun onWithdrawalSuccess()
    fun onWithdrawalFailure()
    fun onIdCheckSuccess()
    fun onIdCheckFailure()
}
package com.maker.hanger.connection

interface ModifyUserView {
    fun onUpdateSuccess()
    fun onUpdateFailure(status: Int)
    fun onWithdrawalSuccess()
    fun onWithdrawalFailure(status: Int)
    fun onIdCheckSuccess()
    fun onIdCheckFailure()
}
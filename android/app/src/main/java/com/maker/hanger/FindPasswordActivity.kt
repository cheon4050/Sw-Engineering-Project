package com.maker.hanger

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.connection.AuthService
import com.maker.hanger.connection.FindPasswordView
import com.maker.hanger.data.UserFindPasswordRequest
import com.maker.hanger.databinding.ActivityFindPasswordBinding

class FindPasswordActivity : AppCompatActivity(), FindPasswordView {
    private lateinit var binding: ActivityFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findPassword()
    }
    
    private fun findPassword() {
        binding.findFindBtn.setOnClickListener {
            if (binding.findIdEt.text.toString().isEmpty()) {
                Toast.makeText(this, "아이디가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.findNicknameEt.text.toString().isEmpty()) {
                Toast.makeText(this, "별명이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val authService = AuthService()
            authService.setFindPasswordView(this)
            authService.find(getUser())
        }
    }

    private fun getUser(): UserFindPasswordRequest {
        val userId : String = binding.findIdEt.text.toString()
        val answer: String = binding.findNicknameEt.text.toString()
        return UserFindPasswordRequest(userId, answer)
    }

    override fun onFindPasswordSuccess(password: String) {
        Log.d("FIND/SUCCESS", "비밀번호 찾기를 성공했습니다.")
        binding.findPasswordTv.text = password
    }

    override fun onFindPasswordFailure() {
        Log.d("FIND/FAILURE", "비밀번호 찾기를 실패했습니다.")
    }
}
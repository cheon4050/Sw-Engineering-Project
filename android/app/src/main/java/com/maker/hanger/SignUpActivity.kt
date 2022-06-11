package com.maker.hanger

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.connection.AuthService
import com.maker.hanger.connection.SignUpView
import com.maker.hanger.data.UserSignUpRequest
import com.maker.hanger.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity(), SignUpView {
    private lateinit var binding: ActivitySignupBinding
    private var isValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userIdCheck()
        signUp()
    }

    private fun userIdCheck() {
        binding.signupIdCheckTv.setOnClickListener {
            if (binding.signupIdEt.text.toString().isEmpty()) {
                Toast.makeText(this, "아이디가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.signupIdEt.text.toString().length < 8 || binding.signupIdEt.text.toString().length > 20) {
                Toast.makeText(this, "사용 가능한 아이디의 길이는 8 ~ 20글자입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val authService = AuthService()
                authService.setSignUpView(this)
                authService.idCheck(binding.signupIdEt.text.toString())
            }
        }
    }

    private fun signUp() {
        binding.signupSignUpBtn.setOnClickListener {
            if (!isValid) {
                Toast.makeText(this, "아이디가 유효하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.signupNicknameEt.text.toString().isEmpty()) {
                Toast.makeText(this, "별명이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.signupPasswordEt.text.toString().isEmpty() || binding.signupPasswordCheckEt.text.toString().isEmpty()) {
                Toast.makeText(this, "비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.signupPasswordEt.text.toString() != binding.signupPasswordCheckEt.text.toString()) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val authService = AuthService()
            authService.setSignUpView(this)
            authService.signUp(getUser())
        }
    }

    private fun getUser(): UserSignUpRequest {
        val userId : String = binding.signupIdEt.text.toString()
        val password: String = binding.signupPasswordEt.text.toString()
        val answer: String = binding.signupNicknameEt.text.toString()
        return UserSignUpRequest(userId, password, answer)
    }

    override fun onSignUpSuccess() {
        Log.d("SIGNUP/SUCCESS", "회원가입을 성공했습니다.")
        finish()
    }

    override fun onSignUpFailure() {
        Log.d("SIGNUP/FAILURE", "회원가입을 실패했습니다.")
        finish()
    }

    @SuppressLint("ResourceAsColor")
    override fun onIdCheckSuccess() {
        Log.d("IDCHECK/SUCCESS", "아이디 중복 확인을 성공했습니다.")
        isValid = true
        binding.signupIdCheckTv.text = "사용 가능"
        binding.signupIdCheckTv.setTextColor(R.color.blue)
    }

    override fun onIdCheckFailure() {
        Log.d("IDCHECK/FAILURE", "아이디 중복 확인을 실패했습니다.")
    }
}
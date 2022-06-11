package com.maker.hanger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.connection.AuthService
import com.maker.hanger.connection.LoginView
import com.maker.hanger.data.UserLoginRequest
import com.maker.hanger.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()
        signUp()
        findPassword()
    }

    private fun login() {
        binding.loginSignUpBtn.setOnClickListener {
            val authService = AuthService()
            authService.setLoginView(this)
            authService.login(getUser())
        }
    }

    private fun signUp() {
        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun findPassword() {
        binding.loginFindPasswordTv.setOnClickListener {
            startActivity(Intent(this, FindPasswordActivity::class.java))
        }
    }

    private fun getUser(): UserLoginRequest {
        val userId : String = binding.loginIdEt.text.toString()
        val password: String = binding.loginPasswordEt.text.toString()
        return UserLoginRequest(userId, password)
    }

    private fun saveJwt(jwt: String) {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("jwt", jwt)
        editor.apply()
    }

    override fun onLoginSuccess(userToken: String) {
        Log.d("LOGIN/SUCCESS", "로그인을 성공했습니다.")
        saveJwt(userToken)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onLoginFailure() {
        Log.d("LOGIN/FAILURE", "로그인을 실패했습니다.")
    }
}
package com.maker.hanger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.connection.AuthService
import com.maker.hanger.connection.LoginView
import com.maker.hanger.data.User
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
            if (binding.loginIdEt.text.toString().isEmpty()) {
                Toast.makeText(this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.loginPasswordEt.text.toString().isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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

    private fun getUser(): User {
        val userId : String = binding.loginIdEt.text.toString()
        val password: String = binding.loginPasswordEt.text.toString()
        return User(userId, password)
    }

    private fun saveJwt(jwt: String?) {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("jwt", jwt)
        editor.apply()
    }

    override fun onLoginSuccess(userToken: String?) {
        Log.d("LOGIN/SUCCESS", "로그인을 성공했습니다.")
        saveJwt(userToken)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onLoginFailure() {
        Log.d("LOGIN/FAILURE", "로그인을 실패했습니다.")
        Toast.makeText(this, "등록되지 않은 아이디이거나 아이디 또는 비밀번호를 잘못 입력했습니다.", Toast.LENGTH_SHORT).show()
    }
}
package com.maker.hanger

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.data.User
import com.maker.hanger.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUp()
    }

    private fun signUp() {
        binding.signupSignUpBtn.setOnClickListener {
            if (binding.signupIdEt.text.toString().isEmpty()) {
                Toast.makeText(this, "아이디 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.signupBirthEt.text.toString().isEmpty()) {
                Toast.makeText(this, "생년월일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.signupPasswordEt.text.toString() != binding.signupPasswordCheckEt.text.toString()) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 서버로 User 객체 넘겨주기
            finish()
        }
    }

    private fun getUser(): User {
        val userId : String = binding.signupIdEt.text.toString()
        val password: String = binding.signupPasswordEt.text.toString()
        return User("1", userId, password)
    }
}
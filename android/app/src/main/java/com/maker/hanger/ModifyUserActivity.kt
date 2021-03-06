package com.maker.hanger

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.connection.AuthService
import com.maker.hanger.connection.ModifyUserView
import com.maker.hanger.data.User
import com.maker.hanger.databinding.ActivityModifyUserBinding

class ModifyUserActivity : AppCompatActivity(), ModifyUserView {
    private lateinit var binding: ActivityModifyUserBinding
    private var isValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userIdCheck()
        updateUser()
        withdrawalUser()
    }

    private fun userIdCheck() {
        binding.modifyIdCheckTv.setOnClickListener {
            if (binding.modifyIdEt.text.toString().isEmpty()) {
                Toast.makeText(this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.modifyIdEt.text.toString().length < 8 || binding.modifyIdEt.text.toString().length > 20) {
                Toast.makeText(this, "사용 가능한 아이디의 길이는 8 ~ 20자입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val authService = AuthService()
                authService.setModifyUserView(this)
                authService.idCheck(binding.modifyIdEt.text.toString(), "modifyUser")
            }
        }
    }

    private fun updateUser() {
        binding.modifyModifyBtn.setOnClickListener {
            if (!isValid) {
                Toast.makeText(this, "아이디가 유효하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.modifyPasswordEt.text.toString().length < 8 || binding.modifyPasswordEt.text.toString().length > 20) {
                Toast.makeText(this, "사용 가능한 비밀번호의 길이는 8 ~ 20자입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.modifyPasswordEt.text.toString().isEmpty() || binding.modifyPasswordCheckEt.text.toString().isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.modifyPasswordEt.text.toString() != binding.modifyPasswordCheckEt.text.toString()) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val authService = AuthService()
            authService.setModifyUserView(this)
            authService.update(getJwt(), getUser())
        }
    }

    private fun withdrawalUser() {
        binding.modifyWithdrawalBtn.setOnClickListener {
            val authService = AuthService()
            authService.setModifyUserView(this)
            authService.delete(getJwt())
        }
    }

    private fun getUser(): User {
        val userId : String = binding.modifyIdEt.text.toString()
        val password: String = binding.modifyPasswordEt.text.toString()
        return User(userId, password)
    }

    private fun getJwt(): String? {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        return sharedPreferences.getString("jwt", null)
    }

    private fun removeJwt() {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("jwt")
        editor.apply()
    }

    override fun onUpdateSuccess() {
        Log.d("UPDATE/SUCCESS", "회원 정보 수정을 성공했습니다.")
        Toast.makeText(this, "회원 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onUpdateFailure(status: Int) {
        Log.d("UPDATE/FAILURE", "회원 정보 수정을 실패했습니다.")
        when (status) {
            400 -> {
                Toast.makeText(this, "회원 정보 수정을 실패했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            else -> {
                Toast.makeText(this, "토큰이 유효하지 않습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show()
                removeJwt()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }
    }

    override fun onWithdrawalSuccess() {
        Log.d("DELETE/SUCCESS", "회원 탈퇴를 성공했습니다.")
        Toast.makeText(this, "그동안 Hanger를 이용해 주셔서 감사합니다.", Toast.LENGTH_SHORT).show()
        removeJwt()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    override fun onWithdrawalFailure(status: Int) {
        Log.d("DELETE/FAILURE", "회원 탈퇴를 실패했습니다.")
        when (status) {
            400 -> {
                Toast.makeText(this, "회원 탈퇴를 실패했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            else -> {
                Toast.makeText(this, "토큰이 유효하지 않습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show()
                removeJwt()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }
    }

    override fun onIdCheckSuccess() {
        Log.d("IDCHECK/SUCCESS", "아이디 중복 확인을 성공했습니다.")
        isValid = true
        binding.modifyIdCheckTv.text = "사용 가능"
        binding.modifyIdCheckTv.setTextColor(Color.BLUE)
    }

    override fun onIdCheckFailure() {
        Log.d("IDCHECK/FAILURE", "아이디 중복 확인을 실패했습니다.")
        binding.modifyIdCheckTv.text = "사용 불가능"
        binding.modifyIdCheckTv.setTextColor(Color.RED)
    }
}
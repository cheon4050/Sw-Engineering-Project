package com.maker.hanger

import android.annotation.SuppressLint
import android.content.Intent
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
                Toast.makeText(this, "아이디가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.modifyIdEt.text.toString().length < 8 || binding.modifyIdEt.text.toString().length > 20) {
                Toast.makeText(this, "사용 가능한 아이디의 길이는 8 ~ 20글자입니다.", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "사용 가능한 비밀번호의 길이는 8 ~ 20글자입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.modifyPasswordEt.text.toString().isEmpty() || binding.modifyPasswordCheckEt.text.toString().isEmpty()) {
                Toast.makeText(this, "비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
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
        finish()
    }

    override fun onUpdateFailure() {
        Log.d("UPDATE/FAILURE", "회원 정보 수정을 실패했습니다.")
        finish()
    }

    override fun onWithdrawalSuccess() {
        Log.d("DELETE/SUCCESS", "회원 탈퇴를 성공했습니다.")
        removeJwt()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    override fun onWithdrawalFailure() {
        Log.d("DELETE/FAILURE", "회원 탈퇴를 실패했습니다.")
    }

    @SuppressLint("ResourceAsColor")
    override fun onIdCheckSuccess() {
        Log.d("IDCHECK/SUCCESS", "아이디 중복 확인을 성공했습니다.")
        isValid = true
        binding.modifyIdCheckTv.text = "사용 가능"
        binding.modifyIdCheckTv.setTextColor(R.color.blue)
    }

    override fun onIdCheckFailure() {
        Log.d("IDCHECK/FAILURE", "아이디 중복 확인을 실패했습니다.")
    }
}
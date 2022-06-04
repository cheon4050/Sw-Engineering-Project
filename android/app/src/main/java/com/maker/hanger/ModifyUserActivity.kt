package com.maker.hanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.data.User
import com.maker.hanger.databinding.ActivityModifyUserBinding

class ModifyUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModifyUserBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modifyUser()
        withdrawalUser()
    }

    private fun modifyUser() {
        binding.modifyModifyBtn.setOnClickListener {
            finish()
        }
    }

    private fun withdrawalUser() {
        binding.modifyWithdrawalBtn.setOnClickListener {
            finish()
        }
    }

    private fun getUser(): User {
        val userId : String = binding.modifyIdEt.text.toString()
        val password: String = binding.modifyPasswordEt.text.toString()
        val birth : Int = Integer.parseInt(binding.modifyBirthEt.text.toString())
        return User(null, userId, password, birth)
    }
}
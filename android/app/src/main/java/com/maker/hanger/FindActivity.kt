package com.maker.hanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.data.User
import com.maker.hanger.databinding.ActivityFindBinding

class FindActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findPassword()
    }
    
    private fun findPassword() {
        binding.findFindBtn.setOnClickListener {
            finish()
        }
    }

    private fun getUser(): User {
        val userId : String = binding.findIdEt.text.toString()
        val password: String = binding.findPasswordEt.text.toString()
        return User(0, userId, password, 0)
    }
}
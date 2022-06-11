package com.maker.hanger

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.databinding.ActivityLogoBinding

class LogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogoBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isLogin()
    }

    private fun isLogin() {
        handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            if (getJwt() == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 1000)
    }

    private fun getJwt(): String? {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        return sharedPreferences.getString("jwt", null)
    }
}
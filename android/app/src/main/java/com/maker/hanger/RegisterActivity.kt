package com.maker.hanger

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var clothes: Clothes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addClothes()
    }

    private fun addClothes() {
        binding.registerClothesAddIv.setOnClickListener {
            Toast.makeText(this, "의류가 등록되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun attachPhoto() {
        // 사진 첨부하기
    }

    private fun selectSeason() {
        // 계절 선택하기
    }

    private fun selectKind() {
        // 종류 선택하기
    }

    private fun selectWashingMethod() {
        // 세탁법 선택하기
    }

    private fun selectSize() {
        // 사이즈 입력하기
    }
}
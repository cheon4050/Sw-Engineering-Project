package com.maker.hanger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.databinding.ActivityWashingmethodBinding

class WashingMethodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWashingmethodBinding
    private var washingMethod: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWashingmethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        washingMethod = intent.getIntExtra("washing", 0)

        setWashingMethodPhoto(washingMethod)
        setWashingMethodText(washingMethod)

        closeWashingMethod()
    }

    private fun setWashingMethodPhoto(washingMethod: Int) {
        when (washingMethod) {
            0 -> binding.washingMethodIv.setImageResource(R.drawable.washing_no)
            40 -> binding.washingMethodIv.setImageResource(R.drawable.washing_40)
            60 -> binding.washingMethodIv.setImageResource(R.drawable.washing_60)
            else -> binding.washingMethodIv.setImageResource(R.drawable.washing_95)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setWashingMethodText(washingMethod: Int) {
        when (washingMethod) {
            0 -> binding.washingMethodTv.text = "물세탁 안됨"
            40 -> binding.washingMethodTv.text = "물 온도 40℃로 세탁\n\n세탁기, 손세탁 가능\n\n세제 종류 제한 없음"
            60 -> binding.washingMethodTv.text = "물 온도 60℃로 세탁\n\n세탁기, 손세탁 가능\n\n세제 종류 제한 없음"
            else -> binding.washingMethodTv.text = "물 온도 95℃로 세탁\n\n세탁기, 손세탁 가능\n\n세제 종류 제한 없음\n\n삶을 수 있음"
        }
    }

    private fun closeWashingMethod() {
        binding.washingMethodCloseIv.setOnClickListener {
            finish()
        }
    }
}
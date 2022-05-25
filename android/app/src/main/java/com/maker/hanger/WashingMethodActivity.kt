package com.maker.hanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.databinding.ActivityWashingmethodBinding

class WashingMethodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWashingmethodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWashingmethodBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
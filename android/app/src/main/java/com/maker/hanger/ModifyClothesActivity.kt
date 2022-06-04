package com.maker.hanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.databinding.ActivityModifyClothesBinding

class ModifyClothesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModifyClothesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyClothesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
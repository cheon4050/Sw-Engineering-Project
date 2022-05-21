package com.maker.hanger

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ActivityDetailedinfoBinding

class DetailedInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedinfoBinding
    private lateinit var clothes: Clothes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clothes = intent.getSerializableExtra("clothes") as Clothes

        initClothesInfo()
        deleteClothes()
    }

    private fun initClothesInfo() {
        binding.detailInfoClothesKindInputTv.text = clothes.kind.toString()
        binding.detailInfoClothesSizeInputTv.text = clothes.size.toString()
        for (i in 0 until clothes.washingMethod.size) {
            when (clothes.washingMethod[i]) {
                40 -> {
                    binding.detailInfoClothesWashing40Iv.visibility = View.VISIBLE
                }
                60 -> {
                    binding.detailInfoClothesWashing60Iv.visibility = View.VISIBLE
                }
                95 -> {
                    binding.detailInfoClothesWashing95Iv.visibility = View.VISIBLE
                }
                else -> {
                    binding.detailInfoClothesWashingNoIv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun deleteClothes() {
        binding.detailInfoClothesDeleteIv.setOnClickListener {
            finish()
        }
    }
}
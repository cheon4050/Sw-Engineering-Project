package com.maker.hanger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
        searchWashingMethod()
    }

    private fun initClothesInfo() {
        isBookmark(clothes.bookmark)
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
            Toast.makeText(this, "의류가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun isBookmark(isLike: Boolean) {
        if (isLike) {
            binding.detailInfoClothesBookmarkOnIv.visibility = View.VISIBLE
            binding.detailInfoClothesBookmarkOffIv.visibility = View.GONE
        } else {
            binding.detailInfoClothesBookmarkOnIv.visibility = View.GONE
            binding.detailInfoClothesBookmarkOffIv.visibility = View.VISIBLE
        }
    }

    private fun searchWashingMethod() {
        binding.detailInfoClothesWashing40Iv.setOnClickListener {
            val intent = Intent(this, WashingMethodActivity::class.java)
            startActivity(intent)
        }
        binding.detailInfoClothesWashing60Iv.setOnClickListener {
            val intent = Intent(this, WashingMethodActivity::class.java)
            startActivity(intent)
        }
        binding.detailInfoClothesWashing95Iv.setOnClickListener {
            val intent = Intent(this, WashingMethodActivity::class.java)
            startActivity(intent)
        }
        binding.detailInfoClothesWashingNoIv.setOnClickListener {
            val intent = Intent(this, WashingMethodActivity::class.java)
            startActivity(intent)
        }
    }
}
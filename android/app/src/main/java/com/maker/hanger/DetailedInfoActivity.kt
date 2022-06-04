package com.maker.hanger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.maker.hanger.connection.ClothesService
import com.maker.hanger.connection.DetailedInfoView
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ActivityDetailedInfoBinding

class DetailedInfoActivity : AppCompatActivity(), DetailedInfoView {
    private lateinit var binding: ActivityDetailedInfoBinding
    private lateinit var clothes: Clothes
    private var isSuccess: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        searchInfoClothes()

        if (isSuccess) {
            searchWashingMethod()
            initClothesInfo()
            deleteClothes()
        }
    }

    private fun searchInfoClothes() {
        val userToken = intent.getStringExtra("userToken")
        val clothesIdx = intent.getStringExtra("clothesIdx")!!.toInt()
        val clothesService = ClothesService()
        clothesService.setDetailedInfoView(this)
        clothesService.searchInfo(userToken, clothesIdx)
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

    private fun deleteClothes() {
        binding.detailInfoClothesDeleteIv.setOnClickListener {
            val userToken = intent.getStringExtra("userToken")
            val clothesIdx = intent.getStringExtra("clothesIdx")!!.toInt()
            val clothesService = ClothesService()
            clothesService.setDetailedInfoView(this)
            clothesService.delete(userToken, clothesIdx)
        }
    }

    private fun initClothesInfo() {
        isBookmark(clothes.bookmark)
        Glide.with(applicationContext).load(clothes.clothesImage).override(350, 480)
            .into(binding.detailInfoClothesIv)
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

    private fun isBookmark(isLike: Boolean) {
        if (isLike) {
            binding.detailInfoClothesBookmarkOnIv.visibility = View.VISIBLE
            binding.detailInfoClothesBookmarkOffIv.visibility = View.GONE
        } else {
            binding.detailInfoClothesBookmarkOnIv.visibility = View.GONE
            binding.detailInfoClothesBookmarkOffIv.visibility = View.VISIBLE
        }
    }

    override fun onSearchInfoSuccess(clothes: Clothes) {
        this.clothes = clothes
        isSuccess = true
        Log.d("SEARCHINFO/SUCCESS", "의류 상세정보 조회를 성공했습니다.")
    }

    override fun onSearchInfoFailure() {
        Log.d("SEARCHINFO/FAILURE", "의류 상세정보 조회를 실패했습니다.")
        finish()
    }

    override fun onDeleteSuccess() {
        Log.d("DELETE/FAILURE", "의류 삭제를 성공했습니다.")
        Toast.makeText(this, "의류가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDeleteFailure() {
        Log.d("DELETE/FAILURE", "의류 삭제를 실패했습니다.")
    }
}
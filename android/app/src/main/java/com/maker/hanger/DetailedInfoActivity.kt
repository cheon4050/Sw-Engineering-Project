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

        closeDetailedInfo()
    }

    override fun onStart() {
        super.onStart()

        searchInfoClothes()

        if (isSuccess) {
            searchWashingMethod()
            initClothesInfo()
            deleteClothes()
            updateClothes()
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
        val intent = Intent(this, WashingMethodActivity::class.java)
        with (binding) {
            detailInfoClothesWashing40Iv.setOnClickListener {
                startActivity(intent)
            }
            detailInfoClothesWashing60Iv.setOnClickListener {
                startActivity(intent)
            }
            detailInfoClothesWashing95Iv.setOnClickListener {
                startActivity(intent)
            }
            detailInfoClothesWashingNoIv.setOnClickListener {
                startActivity(intent)
            }
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

    private fun updateClothes() {
        binding.detailInfoClothesModifyIv.setOnClickListener {
            val intent = Intent(this, ModifyClothesActivity::class.java)
            intent.putExtra("clothes", clothes)
            startActivity(intent)
        }
    }

    private fun initClothesInfo() {
        setBookmark(clothes.bookmark)
        Glide.with(applicationContext).load(clothes.clothesImageUrl).override(350, 480)
            .into(binding.detailInfoClothesIv)
        setDate()
        setSeason()
        setKind()
        setSize()
        setWashingMethod()
    }

    private fun setDate() {
        binding.detailInfoClothesDateInputTv.text = clothes.date
    }

    private fun setSeason() {
        for (season in clothes.season) {
            when (season) {
                "spring" -> binding.detailInfoClothesSpringTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "summer" -> binding.detailInfoClothesSummerTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "autumn" -> binding.detailInfoClothesAutumnTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "winter" -> binding.detailInfoClothesWinterTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            }
        }
    }

    private fun setKind() {
        for (kind in clothes.kind) {
            when (kind) {
                "top" -> binding.detailInfoClothesTopTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "outer" -> binding.detailInfoClothesOuterTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "bottoms" -> binding.detailInfoClothesBottomsTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "onepiece" -> binding.detailInfoClothesOnepieceTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "shoes" -> binding.detailInfoClothesShoesTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "accessories" -> binding.detailInfoClothesAccessoriesTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            }
        }
    }

    private fun setBookmark(isLike: Boolean) {
        if (isLike) {
            binding.detailInfoClothesBookmarkIv.setImageResource(R.drawable.bookmark_on_search)
        } else {
            binding.detailInfoClothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)
        }
    }

    private fun setWashingMethod() {
        for (washingMethod in clothes.washingMethod) {
            when (washingMethod) {
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

    private fun setSize() {
        when (clothes.size) {
            'S' -> binding.detailInfoClothesSmallTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            'M' -> binding.detailInfoClothesMediumTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            'L' -> binding.detailInfoClothesLargeTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
        }
    }

    private fun closeDetailedInfo() {
        binding.detailInfoClothesCloseIv.setOnClickListener {
            finish()
        }
    }

    override fun onSearchInfoSuccess(clothes: Clothes) {
        Log.d("SEARCHINFO/SUCCESS", "의류 상세정보 조회를 성공했습니다.")
        this.clothes = clothes
        isSuccess = true
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
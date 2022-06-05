package com.maker.hanger

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.maker.hanger.connection.ClothesService
import com.maker.hanger.connection.ModifyClothesView
import com.maker.hanger.data.Clothes
import com.maker.hanger.data.ClothesRequest
import com.maker.hanger.databinding.ActivityModifyClothesBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ModifyClothesActivity : AppCompatActivity(), ModifyClothesView {
    private lateinit var binding: ActivityModifyClothesBinding
    private lateinit var file: File
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private val gson = Gson()
    private lateinit var clothes: Clothes
    private var isOriginal: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyClothesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPermission()
        setActivityResultLauncher()
        closeModifyClothes()

        initClothesInfo()
        updateClothes()
    }

    private fun setPermission() {
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                val getPermission = Intent()
                getPermission.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivity(getPermission)
            }
        }
    }

    private fun setActivityResultLauncher() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result -> if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val uri = data?.data as Uri
                    Glide.with(applicationContext).load(uri).override(350, 480)
                        .into(binding.modifyClothesIv)
                    // 이미지 절대 경로
                    Log.d("TEST", getAbsolutelyPath(uri, this))
                    file = File(getAbsolutelyPath(uri, this))
                    isOriginal = false
                }
            }
    }

    private fun getAbsolutelyPath(path: Uri?, context : Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result = cursor?.getString(index!!)

        return result!!
    }

    private fun initClothesInfo() {
        Glide.with(applicationContext).load(clothes.clothesImageUrl).override(350, 480)
            .into(binding.modifyClothesIv)
        setSeason()
        setKind()
        setSize()
        setWashingMethod()
    }

    private fun setSeason() {
        for (season in clothes.season) {
            when (season) {
                "spring" -> binding.modifyClothesSpringTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "summer" -> binding.modifyClothesSummerTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "winter" -> binding.modifyClothesWinterTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "autumn" -> binding.modifyClothesAutumnTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            }
        }
    }

    private fun setKind() {
        for (kind in clothes.kind) {
            when (kind) {
                "top" -> binding.modifyClothesTopTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "outer" -> binding.modifyClothesOuterTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "bottoms" -> binding.modifyClothesBottomsTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "onepiece" -> binding.modifyClothesOnepieceTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "shoes" -> binding.modifyClothesShoesTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
                "accessories" -> binding.modifyClothesAccessoriesTv
                    .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            }
        }
    }

    private fun setWashingMethod() {
        for (washingMethod in clothes.washingMethod) {
            when (washingMethod) {
                40 -> {
                    binding.modifyClothesWashing40Iv.visibility = View.VISIBLE
                }
                60 -> {
                    binding.modifyClothesWashing60Iv.visibility = View.VISIBLE
                }
                95 -> {
                    binding.modifyClothesWashing95Iv.visibility = View.VISIBLE
                }
                else -> {
                    binding.modifyClothesWashingNoIv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setSize() {
        when (clothes.size) {
            'S' -> binding.modifyClothesSmallTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            'M' -> binding.modifyClothesMediumTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
            'L' -> binding.modifyClothesLargeTv
                .setBackgroundResource(R.drawable.clothes_search_select_on_background)
        }
    }

    private fun updateClothes() {
        updatePhoto()
        updateSeason()
        updateKind()
        updateWashingMethod()
        updateSize()

        binding.modifyClothesModifyIv.setOnClickListener {
            if (isOriginal) {
                file = File(clothes.clothesImageUrl)
            }
            val clothesRequest = ClothesRequest(clothes.date, clothes.season, clothes.kind, clothes.washingMethod, clothes.size)
            val clothesRequestBody = gson.toJson(clothesRequest).toRequestBody("application/json; charset=utf-8".toMediaType())
            val fileRequestBody = file.asRequestBody("text/x-markdown; charset=utf-8".toMediaType())
            val multipartBodyPartFile = MultipartBody.Part.createFormData("clothesImage", file.name, fileRequestBody)

            val clothesService = ClothesService()
            clothesService.setModifyClothesView(this)
            clothesService.update("1", multipartBodyPartFile, clothesRequestBody, clothes.clothesIdx)
        }
    }

    private fun updatePhoto() {
        binding.modifyClothesIv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            launcher.launch(intent)
        }
    }

    private fun updateSeason() {
        with(binding) {
            modifyClothesSpringTv.setOnClickListener {
                if (!clothes.season.contains("spring")) {
                    modifyClothesSpringTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.season.add("spring")
                } else {
                    modifyClothesSpringTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.season.remove("spring")
                }
            }
            modifyClothesSummerTv.setOnClickListener {
                if (!clothes.season.contains("summer")) {
                    modifyClothesSummerTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.season.add("summer")
                } else {
                    modifyClothesSummerTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.season.remove("summer")
                }
            }
            modifyClothesAutumnTv.setOnClickListener {
                if (!clothes.season.contains("autumn")) {
                    modifyClothesAutumnTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.season.add("autumn")
                } else {
                    modifyClothesAutumnTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.season.remove("autumn")
                }
            }
            modifyClothesWinterTv.setOnClickListener {
                if (!clothes.season.contains("winter")) {
                    modifyClothesWinterTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.season.add("winter")
                } else {
                    modifyClothesWinterTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.season.remove("winter")
                }
            }
        }
    }

    private fun updateKind() {
        with(binding) {
            modifyClothesTopTv.setOnClickListener {
                if (!clothes.kind.contains("top")) {
                    modifyClothesTopTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.kind.add("top")
                } else {
                    modifyClothesTopTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.kind.remove("top")
                }
            }
            modifyClothesOuterTv.setOnClickListener {
                if (!clothes.kind.contains("outer")) {
                    modifyClothesOuterTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.kind.add("outer")
                } else {
                    modifyClothesOuterTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.kind.remove("outer")
                }
            }
            modifyClothesBottomsTv.setOnClickListener {
                if (!clothes.kind.contains("bottoms")) {
                    modifyClothesBottomsTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.kind.add("bottoms")
                } else {
                    modifyClothesBottomsTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.kind.remove("bottoms")
                }
            }
            modifyClothesOnepieceTv.setOnClickListener {
                if (!clothes.kind.contains("onepiece")) {
                    modifyClothesOnepieceTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.kind.add("onepiece")
                } else {
                    modifyClothesOnepieceTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.kind.remove("onepiece")
                }
            }
            modifyClothesShoesTv.setOnClickListener {
                if (!clothes.kind.contains("shoes")) {
                    modifyClothesShoesTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.kind.add("shoes")
                } else {
                    modifyClothesShoesTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.kind.remove("shoes")
                }
            }
            modifyClothesAccessoriesTv.setOnClickListener {
                if (!clothes.kind.contains("accessories")) {
                    modifyClothesAccessoriesTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.kind.add("accessories")
                } else {
                    modifyClothesAccessoriesTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.kind.remove("accessories")
                }
            }
        }
    }

    private fun updateWashingMethod() {
        with(binding) {
            modifyClothesWashing40Iv.setOnClickListener {
                if (!clothes.washingMethod.contains(40)) {
                    modifyClothesWashing40Iv.setImageResource(R.drawable.washing_select)
                    clothes.washingMethod.add(40)
                } else {
                    modifyClothesWashing40Iv.setImageResource(R.drawable.washing_40)
                    clothes.washingMethod.remove(40)
                }
            }
            modifyClothesWashing60Iv.setOnClickListener {
                if (!clothes.washingMethod.contains(60)) {
                    modifyClothesWashing60Iv.setImageResource(R.drawable.washing_select)
                    clothes.washingMethod.add(60)
                } else {
                    modifyClothesWashing60Iv.setImageResource(R.drawable.washing_60)
                    clothes.washingMethod.remove(60)
                }
            }
            modifyClothesWashing95Iv.setOnClickListener {
                if (!clothes.washingMethod.contains(95)) {
                    modifyClothesWashing95Iv.setImageResource(R.drawable.washing_select)
                    clothes.washingMethod.add(95)
                } else {
                    modifyClothesWashing95Iv.setImageResource(R.drawable.washing_95)
                    clothes.washingMethod.remove(95)
                }
            }
            modifyClothesWashingNoIv.setOnClickListener {
                if (!clothes.washingMethod.contains(0)) {
                    modifyClothesWashingNoIv.setImageResource(R.drawable.washing_select)
                    clothes.washingMethod.add(0)
                } else {
                    modifyClothesWashingNoIv.setImageResource(R.drawable.washing_no)
                    clothes.washingMethod.remove(0)
                }
            }
        }
    }

    private fun updateSize() {
        with(binding) {
            modifyClothesSmallTv.setOnClickListener {
                if (clothes.size != 'S') {
                    modifyClothesSmallTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    modifyClothesMediumTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    modifyClothesLargeTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.size = 'S'
                } else {
                    modifyClothesSmallTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.size = 'X'
                }
            }
            modifyClothesMediumTv.setOnClickListener {
                if (clothes.size != 'M') {
                    modifyClothesSmallTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    modifyClothesMediumTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    modifyClothesLargeTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.size = 'M'
                } else {
                    modifyClothesMediumTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.size = 'X'
                }
            }
            modifyClothesLargeTv.setOnClickListener {
                if (clothes.size != 'L') {
                    modifyClothesSmallTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    modifyClothesMediumTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    modifyClothesLargeTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    clothes.size = 'L'
                } else {
                    modifyClothesLargeTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    clothes.size = 'X'
                }
            }
        }
    }

    private fun closeModifyClothes() {
        binding.modifyClothesCloseIv.setOnClickListener {
            finish()
        }
    }

    override fun onUpdateSuccess() {
        Log.d("UPDATE/SUCCESS", "의류 수정을 성공했습니다.")
        Toast.makeText(this,"의류를 수정했습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onUpdateFailure() {
        Log.d("UPDATE/FAILURE", "의류 수정을 실패했습니다.")
        Toast.makeText(this,"의류 수정을 실패했습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }
}
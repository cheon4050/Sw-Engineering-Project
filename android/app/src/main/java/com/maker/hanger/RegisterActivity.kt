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
import com.maker.hanger.connection.ClothesService
import com.maker.hanger.connection.RegisterView
import com.maker.hanger.data.ClothesRequest
import com.maker.hanger.databinding.ActivityRegisterBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var absolutelyPath: String
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private val season: ArrayList<String> = ArrayList()
    private val kind: ArrayList<String> = ArrayList()
    private var size: Char = 'X'
    private val washingMethod: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPermission()
        setActivityResultLauncher()
        closeRegister()

        attachPhoto()
        selectSeason()
        selectKind()
        selectWashingMethod()
        selectSize()

        addClothes()
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
                        .into(binding.registerClothesIv)
                    binding.registerClothesPhotoAddIv.visibility = View.GONE
                    // 이미지 절대 경로
                    Log.d("TEST", getAbsolutelyPath(uri, this))
                    absolutelyPath = getAbsolutelyPath(uri, this)
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

    private fun addClothes() {
        binding.registerClothesAddIv.setOnClickListener {
            if (::absolutelyPath.isInitialized) {
                val date = SimpleDateFormat("yyyy/MM/dd").format(Date())
                val clothesRequest = ClothesRequest(absolutelyPath, date, season, kind, washingMethod, size)

                val clothesService = ClothesService()
                clothesService.setRegisterView(this)
                clothesService.add(getJwt(), clothesRequest)
            } else {
                Toast.makeText(this, "의류 사진을 첨부해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun attachPhoto() {
        binding.registerClothesPhotoAddIv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            launcher.launch(intent)
        }
    }

    private fun selectSeason() {
        with(binding) {
            registerClothesSpringTv.setOnClickListener {
                if (!season.contains("spring")) {
                    registerClothesSpringTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    season.add("spring")
                } else {
                    registerClothesSpringTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    season.remove("spring")
                }
            }
            registerClothesSummerTv.setOnClickListener {
                if (!season.contains("summer")) {
                    registerClothesSummerTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    season.add("summer")
                } else {
                    registerClothesSummerTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    season.remove("summer")
                }
            }
            registerClothesAutumnTv.setOnClickListener {
                if (!season.contains("autumn")) {
                    registerClothesAutumnTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    season.add("autumn")
                } else {
                    registerClothesAutumnTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    season.remove("autumn")
                }
            }
            registerClothesWinterTv.setOnClickListener {
                if (!season.contains("winter")) {
                    registerClothesWinterTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    season.add("winter")
                } else {
                    registerClothesWinterTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    season.remove("winter")
                }
            }
        }
    }

    private fun selectKind() {
        with(binding) {
            registerClothesTopTv.setOnClickListener {
                if (!kind.contains("top")) {
                    registerClothesTopTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("top")
                } else {
                    registerClothesTopTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("top")
                }
            }
            registerClothesOuterTv.setOnClickListener {
                if (!kind.contains("outer")) {
                    registerClothesOuterTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("outer")
                } else {
                    registerClothesOuterTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("outer")
                }
            }
            registerClothesBottomsTv.setOnClickListener {
                if (!kind.contains("bottoms")) {
                    registerClothesBottomsTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("bottoms")
                } else {
                    registerClothesBottomsTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("bottoms")
                }
            }
            registerClothesOnepieceTv.setOnClickListener {
                if (!kind.contains("onepiece")) {
                    registerClothesOnepieceTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("onepiece")
                } else {
                    registerClothesOnepieceTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("onepiece")
                }
            }
            registerClothesShoesTv.setOnClickListener {
                if (!kind.contains("shoes")) {
                    registerClothesShoesTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("shoes")
                } else {
                    registerClothesShoesTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("shoes")
                }
            }
            registerClothesAccessoriesTv.setOnClickListener {
                if (!kind.contains("accessories")) {
                    registerClothesAccessoriesTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("accessories")
                } else {
                    registerClothesAccessoriesTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("accessories")
                }
            }
        }
    }

    private fun selectWashingMethod() {
        with(binding) {
            registerClothesWashing40Iv.setOnClickListener {
                if (!washingMethod.contains(40)) {
                    registerClothesWashing40Iv.setImageResource(R.drawable.washing_select)
                    washingMethod.add(40)
                } else {
                    registerClothesWashing40Iv.setImageResource(R.drawable.washing_40)
                    washingMethod.remove(40)
                }
            }
            registerClothesWashing60Iv.setOnClickListener {
                if (!washingMethod.contains(60)) {
                    registerClothesWashing60Iv.setImageResource(R.drawable.washing_select)
                    washingMethod.add(60)
                } else {
                    registerClothesWashing60Iv.setImageResource(R.drawable.washing_60)
                    washingMethod.remove(60)
                }
            }
            registerClothesWashing95Iv.setOnClickListener {
                if (!washingMethod.contains(95)) {
                    registerClothesWashing95Iv.setImageResource(R.drawable.washing_select)
                    washingMethod.add(95)
                } else {
                    registerClothesWashing95Iv.setImageResource(R.drawable.washing_95)
                    washingMethod.remove(95)
                }
            }
            registerClothesWashingNoIv.setOnClickListener {
                if (!washingMethod.contains(0)) {
                    registerClothesWashingNoIv.setImageResource(R.drawable.washing_select)
                    washingMethod.add(0)
                } else {
                    registerClothesWashingNoIv.setImageResource(R.drawable.washing_no)
                    washingMethod.remove(0)
                }
            }
        }
    }

    private fun selectSize() {
        with(binding) {
            registerClothesSmallTv.setOnClickListener {
                if (size != 'S') {
                    registerClothesSmallTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    registerClothesMediumTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    registerClothesLargeTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    size = 'S'
                } else {
                    registerClothesSmallTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    size = 'X'
                }
            }
            registerClothesMediumTv.setOnClickListener {
                if (size != 'M') {
                    registerClothesSmallTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    registerClothesMediumTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    registerClothesLargeTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    size = 'M'
                } else {
                    registerClothesMediumTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    size = 'X'
                }
            }
            registerClothesLargeTv.setOnClickListener {
                if (size != 'L') {
                    registerClothesSmallTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    registerClothesMediumTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    registerClothesLargeTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    size = 'L'
                } else {
                    registerClothesLargeTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    size = 'X'
                }
            }
        }
    }

    private fun closeRegister() {
        binding.registerClothesCloseIv.setOnClickListener {
            finish()
        }
    }

    private fun getJwt(): String? {
        val sharedPreferences = getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getString("jwt", null)
    }

    override fun onRegisterSuccess() {
        Log.d("ADD/SUCCESS", "의류 등록을 성공했습니다.")
        Toast.makeText(this,"의류를 등록했습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onRegisterFailure() {
        Log.d("ADD/FAILURE", "의류 등록을 실패했습니다.")
        Toast.makeText(this,"의류 등록을 실패했습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }
}
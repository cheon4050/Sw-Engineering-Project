package com.maker.hanger

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.maker.hanger.connection.ClothesService
import com.maker.hanger.connection.RegisterView
import com.maker.hanger.data.ClothesRequest
import com.maker.hanger.databinding.ActivityRegisterBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var clothes: ClothesRequest
    private lateinit var gson: Gson
    private lateinit var file: File

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActivityResultLauncher()

        attachPhoto()
        addClothes()
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
                    Log.d("TEST", getAbsolutelyPath(uri, this).toString())
                    file = File(getAbsolutelyPath(uri, this))
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
            //Toast.makeText(this, "의류가 등록되었습니다.", Toast.LENGTH_SHORT).show()
            //finish()

            // dummy
            val data = "990213"
            val season = ArrayList<String>()
            season.apply {
                add("봄")
                add("여름")
            }
            val kind = ArrayList<String>()
            kind.apply {
                add("상의")
            }
            val washingMethod = ArrayList<Int>()
            washingMethod.apply {
                add(1)
                add(2)
            }
            clothes = ClothesRequest(data, season, kind, washingMethod)
            val clothesBody = gson.toJson(clothes).toRequestBody("application/json; charset=utf-8".toMediaType())

            val fileBody = file.asRequestBody("text/x-markdown; charset=utf-8".toMediaType())
            val requestFile = MultipartBody.Part.createFormData("clothesImage", file.name, fileBody)

            val clothesService = ClothesService()
            clothesService.setRegisterView(this)

            clothesService.add("1", requestFile, clothesBody)
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

    override fun onRegisterSuccess() {
        Log.d("ADD/SUCCESS", "의류 등록을 성공했습니다.")
        finish()
    }

    override fun onRegisterFailure() {
        Log.d("ADD/FAILURE", "의류 등록을 실패했습니다.")
        finish()
    }
}
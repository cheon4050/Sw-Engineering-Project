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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var clothes: Clothes

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
            Toast.makeText(this, "의류가 등록되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
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
}
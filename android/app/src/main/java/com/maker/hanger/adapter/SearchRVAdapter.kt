package com.maker.hanger.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maker.hanger.LoginActivity
import com.maker.hanger.R
import com.maker.hanger.connection.BookmarkView
import com.maker.hanger.connection.ClothesService
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ItemClothesBinding

class SearchRVAdapter(private val userToken: String?, private val clothes: ArrayList<Clothes>, private val context: Context) : RecyclerView.Adapter<SearchRVAdapter.ViewHolder>(), BookmarkView {
    interface OnItemClickListener {
        fun onDetailedInfoItem(clothes: Clothes)
        fun onBookmarkItem(position: Int, binding: ItemClothesBinding)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun onBookmark(position: Int) {
        clothes[position].bookmark = !clothes[position].bookmark
        val clothesService = ClothesService()
        clothesService.setBookmarkView(this)
        clothesService.bookmark(userToken, clothes[position].clothesIdx, clothes[position].bookmark)
    }

    fun isBookmark(position: Int, binding: ItemClothesBinding) {
        if (clothes[position].bookmark) {
            binding.clothesBookmarkIv.setImageResource(R.drawable.bookmark_on_search)
        } else {
            binding.clothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemClothesBinding = ItemClothesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clothes[position])
        holder.binding.clothesCardView.setOnClickListener {
            onItemClickListener.onDetailedInfoItem(clothes[position])
        }
        holder.binding.clothesBookmarkIv.setOnClickListener {
            onItemClickListener.onBookmarkItem(position, holder.binding)
        }
    }

    override fun getItemCount(): Int = clothes.size

    inner class ViewHolder(val binding: ItemClothesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clothes: Clothes) {
            Glide.with(itemView).load(clothes.clothesImageUrl).override(150, 150)
                .into(binding.clothesIv)
            if (clothes.bookmark) {
                binding.clothesBookmarkIv.setImageResource(R.drawable.bookmark_on_search)
            } else {
                binding.clothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)
            }
        }
    }

    private fun removeJwt() {
        val sharedPreferences = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("jwt")
        editor.apply()
    }

    override fun onBookmarkSuccess() {
        Log.d("BOOKMARK/SUCCESS", "의류 즐겨찾기를 성공했습니다.")
    }

    override fun onBookmarkFailure(status: Int) {
        Log.d("BOOKMARK/FAILURE", "의류 즐겨찾기를 실패했습니다.")
        when (status) {
            400 -> {
                Toast.makeText(context,"의류 즐겨찾기를 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "토큰이 유효하지 않습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show()
                removeJwt()
                context.startActivity(Intent(context, LoginActivity::class.java))
                (context as Activity).finishAffinity()
            }
        }
    }
}
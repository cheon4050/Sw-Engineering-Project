package com.maker.hanger.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maker.hanger.R
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ItemClothesBinding

class SearchRVAdapter(private val clothes: ArrayList<Clothes>) : RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {
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
            binding.clothesKindTv.text = clothes.kind.toString()
            binding.clothesSizeTv.text = clothes.size.toString()
            if (clothes.bookmark) {
                binding.clothesBookmarkIv.setImageResource(R.drawable.bookmark_on_search)
            } else {
                binding.clothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)
            }
        }
    }
}
package com.maker.hanger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ItemClothesBinding

class SearchRVAdapter(private val clothes: ArrayList<Clothes>) : RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onDetailedInfoItem(clothes: Clothes)
        fun onBookmarkOnItem(position: Int, binding: ItemClothesBinding)
        fun onBookmarkOffItem(position: Int, binding: ItemClothesBinding)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun onBookmarkOn(position: Int) {
        clothes[position].bookmark = true
    }

    fun onBookmarkOff(position: Int) {
        clothes[position].bookmark = false
    }

    fun isBookmark(position: Int, binding: ItemClothesBinding) {
        if (clothes[position].bookmark) {
            binding.clothesBookmarkOnIv.visibility = View.VISIBLE
            binding.clothesBookmarkOffIv.visibility = View.GONE
        } else {
            binding.clothesBookmarkOnIv.visibility = View.GONE
            binding.clothesBookmarkOffIv.visibility = View.VISIBLE
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
        holder.binding.clothesBookmarkOffIv.setOnClickListener {
            onItemClickListener.onBookmarkOnItem(position, holder.binding)
        }
        holder.binding.clothesBookmarkOnIv.setOnClickListener {
            onItemClickListener.onBookmarkOffItem(position, holder.binding)
        }
    }

    override fun getItemCount(): Int = clothes.size

    inner class ViewHolder(val binding: ItemClothesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clothes: Clothes) {
            binding.clothesKindTv.text = clothes.kind.toString()
            binding.clothesSizeTv.text = clothes.size.toString()
        }
    }
}
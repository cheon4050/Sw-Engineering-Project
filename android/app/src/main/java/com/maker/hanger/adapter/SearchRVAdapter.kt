package com.maker.hanger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.ItemClothesBinding

class SearchRVAdapter(private val clothes: ArrayList<Clothes>) : RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onDetailedInfoItem(clothes: Clothes)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemClothesBinding = ItemClothesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clothes[position])
        holder.binding.clothesCardView.setOnClickListener { onItemClickListener.onDetailedInfoItem(clothes[position]) }
    }

    override fun getItemCount(): Int = clothes.size

    inner class ViewHolder(val binding: ItemClothesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clothes: Clothes) {
            binding.clothesKindTv.text = clothes.kind.toString()
            binding.clothesSizeTv.text = clothes.size.toString()
        }
    }
}
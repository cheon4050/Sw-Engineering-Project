package com.maker.hanger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.maker.hanger.databinding.FragmentRecommendBinding

class RecommendFragment(private val clothesImageUrl: String) : Fragment() {
    private lateinit var binding: FragmentRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendBinding.inflate(inflater, container, false)

        setRecommendClothes()

        return binding.root
    }

    private fun setRecommendClothes() {
        Glide.with(this).load(clothesImageUrl).override(360, 360)
            .into(binding.homeRecommendBackgroundIv)
    }
}
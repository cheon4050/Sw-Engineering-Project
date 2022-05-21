package com.maker.hanger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maker.hanger.databinding.FragmentRecommendBinding

class RecommendFragment(private val imageResource: Int = -1) : Fragment() {
    private lateinit var binding: FragmentRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendBinding.inflate(inflater, container, false)
        if (imageResource != -1) {
            binding.homeRecommendBackgroundIv.setImageResource(imageResource)
        }

        return binding.root
    }
}
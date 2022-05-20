package com.maker.hanger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maker.hanger.databinding.FragmentPanelBinding

class PanelFragment(private val imageResource: Int = -1) : Fragment() {
    lateinit var binding: FragmentPanelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPanelBinding.inflate(inflater, container, false)
        if (imageResource != -1) {
            binding.homePanelBackgroundIv.setImageResource(imageResource)
        }

        return binding.root
    }
}
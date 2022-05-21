package com.maker.hanger

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maker.hanger.adapter.RecommendVPAdapter
import com.maker.hanger.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val recommendVPAdapter = RecommendVPAdapter(this)
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        binding.homeRecommendVp.adapter = recommendVPAdapter
        binding.homeIndicator.setViewPager2(binding.homeRecommendVp)

        handler = Handler(Looper.getMainLooper())
        val autoViewPager = AutoViewPager(recommendVPAdapter)
        autoViewPager.start()

        return binding.root
    }

    private fun recommendClothes(recommendAdapter: RecommendVPAdapter) {
        position = (position + 1) % recommendAdapter.itemCount
        binding.homeRecommendVp.setCurrentItem(position, true)
    }

    inner class AutoViewPager(private val recommendAdapter: RecommendVPAdapter) : Thread() {
        override fun run() {
            super.run()
            try {
                while (true) {
                    sleep(2500)
                    handler.post {
                        recommendClothes(recommendAdapter)
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("PANEL", "Home Panel Thread is dead.")
            }
        }
    }
}
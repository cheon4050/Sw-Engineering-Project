package com.maker.hanger

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maker.hanger.adapter.PanelVPAdapter
import com.maker.hanger.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val panelAdapter = PanelVPAdapter(this)
        panelAdapter.addFragment(PanelFragment())
        panelAdapter.addFragment(PanelFragment())
        panelAdapter.addFragment(PanelFragment())
        panelAdapter.addFragment(PanelFragment())
        panelAdapter.addFragment(PanelFragment())
        panelAdapter.addFragment(PanelFragment())
        binding.homePanelVp.adapter = panelAdapter
        binding.homeIndicator.setViewPager2(binding.homePanelVp)

        handler = Handler(Looper.getMainLooper())
        val autoViewPager = AutoViewPager(panelAdapter)
        autoViewPager.start()

        return binding.root
    }

    private fun switchPanel(panelAdapter: PanelVPAdapter) {
        position = (position + 1) % panelAdapter.itemCount
        binding.homePanelVp.setCurrentItem(position, true)
    }

    inner class AutoViewPager(private val panelAdapter: PanelVPAdapter) : Thread() {
        override fun run() {
            super.run()
            try {
                while (true) {
                    sleep(2500)
                    handler.post {
                        switchPanel(panelAdapter)
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("PANEL", "Home Panel Thread is dead.")
            }
        }
    }
}
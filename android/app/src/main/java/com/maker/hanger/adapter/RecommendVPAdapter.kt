package com.maker.hanger.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RecommendVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val recommendFragment: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = recommendFragment.size

    override fun createFragment(position: Int): Fragment = recommendFragment[position]

    fun addFragment(fragment: Fragment) {
        recommendFragment.add(fragment)
        notifyItemInserted(recommendFragment.size - 1)
    }
}
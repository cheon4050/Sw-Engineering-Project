package com.maker.hanger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.maker.hanger.adapter.SearchRVAdapter
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchRVAdapter: SearchRVAdapter
    private val dummyClothes: ArrayList<Clothes> = ArrayList()
    private val dummyKind1: ArrayList<String> = ArrayList()
    private val dummyKind2: ArrayList<String> = ArrayList()
    private val dummyKind3: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        dummyKind1.add("상의")
        dummyKind2.add("하의")
        dummyKind3.add("원피스")
        dummyClothes.apply {
            add(Clothes(1, "", "", "", dummyKind1, ArrayList<Int>(), 'S', false))
            add(Clothes(2, "", "", "", dummyKind2, ArrayList<Int>(), 'M', false))
            add(Clothes(3, "", "", "", dummyKind3, ArrayList<Int>(), 'L', false))
            add(Clothes(4, "", "", "", dummyKind1, ArrayList<Int>(), 'S', false))
            add(Clothes(5, "", "", "", dummyKind3, ArrayList<Int>(), 'L', false))
            add(Clothes(6, "", "", "", dummyKind1, ArrayList<Int>(), 'S', false))
        }
        Log.d("SSS", dummyClothes.size.toString())

        initRecyclerView(dummyClothes)
        return binding.root
    }

    private fun initRecyclerView(clothes: ArrayList<Clothes>) {
        searchRVAdapter = SearchRVAdapter(clothes)
        binding.searchClothesRv.adapter = searchRVAdapter
        binding.searchClothesRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }
}
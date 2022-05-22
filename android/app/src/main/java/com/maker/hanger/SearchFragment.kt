package com.maker.hanger

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.maker.hanger.adapter.SearchRVAdapter
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.FragmentSearchBinding
import com.maker.hanger.databinding.ItemClothesBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchRVAdapter: SearchRVAdapter
    private val dummyClothes: ArrayList<Clothes> = ArrayList()
    private val dummyKind1: ArrayList<String> = ArrayList()
    private val dummyKind2: ArrayList<String> = ArrayList()
    private val dummyKind3: ArrayList<String> = ArrayList()
    private val dummyWashing1: ArrayList<Int> = ArrayList()
    private val dummyWashing2: ArrayList<Int> = ArrayList()
    private val dummyWashing3: ArrayList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        dummyInput()
        initRecyclerView(dummyClothes)
        addClothes()

        return binding.root
    }

    private fun initRecyclerView(clothes: ArrayList<Clothes>) {
        searchRVAdapter = SearchRVAdapter(clothes)
        binding.searchClothesRv.adapter = searchRVAdapter
        binding.searchClothesRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        searchRVAdapter.setOnItemClickListener(object: SearchRVAdapter.OnItemClickListener {
            override fun onDetailedInfoItem(clothes: Clothes) {
                val intent = Intent(requireContext(), DetailedInfoActivity::class.java)
                intent.putExtra("clothes", clothes)
                startActivity(intent)
            }

            override fun onBookmarkOnItem(position: Int, binding: ItemClothesBinding) {
                searchRVAdapter.onBookmarkOn(position)
                searchRVAdapter.isBookmark(position, binding)
            }

            override fun onBookmarkOffItem(position: Int, binding: ItemClothesBinding) {
                searchRVAdapter.onBookmarkOff(position)
                searchRVAdapter.isBookmark(position, binding)
            }
        })
    }

    private fun addClothes() {
        binding.searchClothesAddIv.setOnClickListener {
            startActivity(Intent(requireContext(), RegisterActivity::class.java))
        }
    }

    private fun dummyInput() {
        dummyKind1.add("상의")
        dummyKind2.add("하의")
        dummyKind3.add("원피스")
        dummyWashing1.add(40)
        dummyWashing2.add(40)
        dummyWashing2.add(60)
        dummyWashing3.add(95)
        dummyWashing3.add(0)
        dummyClothes.apply {
            add(Clothes(1, "", "", "", dummyKind1, dummyWashing1, 'S', false))
            add(Clothes(2, "", "", "", dummyKind2, dummyWashing2, 'M', false))
            add(Clothes(3, "", "", "", dummyKind3, dummyWashing3, 'L', false))
            add(Clothes(4, "", "", "", dummyKind1, dummyWashing1, 'S', false))
            add(Clothes(5, "", "", "", dummyKind2, dummyWashing2, 'M', false))
            add(Clothes(6, "", "", "", dummyKind3, dummyWashing3, 'L', false))
        }
    }
}
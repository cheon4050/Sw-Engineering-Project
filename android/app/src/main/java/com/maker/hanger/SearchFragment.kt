package com.maker.hanger

import android.content.Intent
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
import com.maker.hanger.databinding.ItemClothesBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchRVAdapter: SearchRVAdapter
    private var isBookmark: Boolean = false

    // dummy clothes input
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
        addClothes()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        isBookmark = false
        binding.searchClothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)

        search()
        searchBookmark()
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

            override fun onBookmarkItem(position: Int, binding: ItemClothesBinding) {
                searchRVAdapter.onBookmark(position)
                searchRVAdapter.isBookmark(position, binding)
            }
        })
    }

    private fun search() {
        // 의류 조회
        initRecyclerView(dummyClothes)
    }

    private fun addClothes() {
        binding.searchClothesAddIv.setOnClickListener {
            startActivity(Intent(requireContext(), RegisterActivity::class.java))
        }
    }

    private fun selectSession() {
        // 계절 선택
    }

    private fun selectKind() {
        // 종류 선택
    }

    private fun searchCategory() {
        // 카테고리별 조회
    }

    private fun searchBookmark() {
        // 즐겨찾기별 조회
        binding.searchClothesBookmarkIv.setOnClickListener {
            if (!isBookmark) {
                isBookmark = true
                binding.searchClothesBookmarkIv.setImageResource(R.drawable.bookmark_on_search)
                initRecyclerView(bookmarkDummyInput())
            } else {
                isBookmark = false
                binding.searchClothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)
                initRecyclerView(dummyClothes)
            }
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

    private fun bookmarkDummyInput() : ArrayList<Clothes> {
        val bookmarkDummyClothes = ArrayList<Clothes>()
        for (clothes in dummyClothes) {
            if (clothes.bookmark) bookmarkDummyClothes.add(clothes)
        }
        return bookmarkDummyClothes
    }
}
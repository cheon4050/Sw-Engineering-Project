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
import com.maker.hanger.connection.ClothesService
import com.maker.hanger.connection.SearchView
import com.maker.hanger.data.Clothes
import com.maker.hanger.databinding.FragmentSearchBinding
import com.maker.hanger.databinding.ItemClothesBinding

class SearchFragment : Fragment(), SearchView {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchRVAdapter: SearchRVAdapter
    private var season: ArrayList<String> = ArrayList()
    private var kind: ArrayList<String> = ArrayList()
    private var isBookmark: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        addClothes()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        isBookmark = false
        binding.searchClothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)

        searchClothes()
        selectSeason()
        selectKind()
        selectBookmark()
    }

    private fun initRecyclerView(clothes: ArrayList<Clothes>) {
        searchRVAdapter = SearchRVAdapter(clothes)
        binding.searchClothesRv.adapter = searchRVAdapter
        binding.searchClothesRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        searchRVAdapter.setOnItemClickListener(object: SearchRVAdapter.OnItemClickListener {
            override fun onDetailedInfoItem(clothes: Clothes) {
                val intent = Intent(requireContext(), DetailedInfoActivity::class.java)
                intent.putExtra("userToken", "1")
                intent.putExtra("clothesIdx", clothes.clothesIdx.toString())
                startActivity(intent)
            }

            override fun onBookmarkItem(position: Int, binding: ItemClothesBinding) {
                searchRVAdapter.onBookmark(position)
                searchRVAdapter.isBookmark(position, binding)
            }
        })
    }

    private fun addClothes() {
        binding.searchClothesAddIv.setOnClickListener {
            startActivity(Intent(requireContext(), RegisterActivity::class.java))
        }
    }

    private fun searchClothes() {
        val clothesService = ClothesService()
        clothesService.setSearchView(this)
        clothesService.search("1", season, kind, isBookmark)
    }

    private fun selectSeason() {
        with(binding) {
            searchClothesSpringTv.setOnClickListener {
                if (!season.contains("spring")) {
                    searchClothesSpringTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    season.add("spring")
                } else {
                    searchClothesSpringTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    season.remove("spring")
                }
                searchClothes()
            }
            searchClothesSummerTv.setOnClickListener {
                if (!season.contains("summer")) {
                    searchClothesSummerTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    season.add("summer")
                } else {
                    searchClothesSummerTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    season.remove("summer")
                }
                searchClothes()
            }
            searchClothesAutumnTv.setOnClickListener {
                if (!season.contains("autumn")) {
                    searchClothesAutumnTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    season.add("autumn")
                } else {
                    searchClothesAutumnTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    season.remove("autumn")
                }
                searchClothes()
            }
            searchClothesWinterTv.setOnClickListener {
                if (!season.contains("winter")) {
                    searchClothesWinterTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    season.add("winter")
                } else {
                    searchClothesWinterTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    season.remove("winter")
                }
                searchClothes()
            }
        }
    }

    private fun selectKind() {
        with(binding) {
            searchClothesTopTv.setOnClickListener {
                if (!kind.contains("top")) {
                    searchClothesTopTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("top")
                } else {
                    searchClothesTopTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("top")
                }
                searchClothes()
            }
            searchClothesOuterTv.setOnClickListener {
                if (!kind.contains("outer")) {
                    searchClothesOuterTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("outer")
                } else {
                    searchClothesOuterTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("outer")
                }
                searchClothes()
            }
            searchClothesBottomsTv.setOnClickListener {
                if (!kind.contains("bottoms")) {
                    searchClothesBottomsTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("bottoms")
                } else {
                    searchClothesBottomsTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("bottoms")
                }
                searchClothes()
            }
            searchClothesOnepieceTv.setOnClickListener {
                if (!kind.contains("onepiece")) {
                    searchClothesOnepieceTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("onepiece")
                } else {
                    searchClothesOnepieceTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("onepiece")
                }
                searchClothes()
            }
            searchClothesShoesTv.setOnClickListener {
                if (!kind.contains("shoes")) {
                    searchClothesShoesTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("shoes")
                } else {
                    searchClothesShoesTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("shoes")
                }
                searchClothes()
            }
            searchClothesAccessoriesTv.setOnClickListener {
                if (!kind.contains("accessories")) {
                    searchClothesAccessoriesTv.setBackgroundResource(R.drawable.clothes_search_select_on_background)
                    kind.add("accessories")
                } else {
                    searchClothesAccessoriesTv.setBackgroundResource(R.drawable.clothes_search_select_off_background)
                    kind.remove("accessories")
                }
                searchClothes()
            }
        }
    }

    private fun selectBookmark() {
        binding.searchClothesBookmarkIv.setOnClickListener {
            if (!isBookmark) {
                isBookmark = true
                binding.searchClothesBookmarkIv.setImageResource(R.drawable.bookmark_on_search)
            } else {
                isBookmark = false
                binding.searchClothesBookmarkIv.setImageResource(R.drawable.bookmark_off_search)
            }
            searchClothes()
        }
    }

    override fun onSearchSuccess(clothes: ArrayList<Clothes>) {
        Log.d("SEARCH/SUCCESS", "의류 조회를 성공했습니다.")
        initRecyclerView(clothes)
    }

    override fun onSearchFailure() {
        Log.d("SEARCH/FAILURE", "의류 조회를 실패했습니다.")
    }
}
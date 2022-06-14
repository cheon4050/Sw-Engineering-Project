package com.maker.hanger.connection

import android.util.Log
import com.maker.hanger.data.ClothesRequest
import com.maker.hanger.data.ClothesResponse
import com.maker.hanger.data.ClothesSearchInfoResponse
import com.maker.hanger.data.ClothesSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClothesService {
    private lateinit var registerView: RegisterView
    private lateinit var searchView: SearchView
    private lateinit var bookmarkView: BookmarkView
    private lateinit var detailedInfoView: DetailedInfoView
    private lateinit var modifyClothesView: ModifyClothesView

    fun setRegisterView(registerView: RegisterView) {
        this.registerView = registerView
    }

    fun setSearchView(searchView: SearchView) {
        this.searchView = searchView
    }

    fun setBookmarkView(bookmarkView: BookmarkView) {
        this.bookmarkView = bookmarkView
    }

    fun setDetailedInfoView(detailedInfoView: DetailedInfoView) {
        this.detailedInfoView = detailedInfoView
    }

    fun setModifyClothesView(modifyClothesView: ModifyClothesView) {
        this.modifyClothesView = modifyClothesView
    }

    fun add(userToken: String?, clothes: ClothesRequest) {
        val clothesService = getRetrofit().create(ClothesRetrofitInterface::class.java)
        clothesService.add(userToken, clothes).enqueue(object: Callback<ClothesResponse> {
            override fun onResponse(call: Call<ClothesResponse>, response: Response<ClothesResponse>) {
                Log.d("ADD/SUCCESS", response.toString())
                if (response.code() == 400) {
                    registerView.onRegisterFailure()
                } else {
                    registerView.onRegisterSuccess()
                }
            }

            override fun onFailure(call: Call<ClothesResponse>, t: Throwable) {
                Log.d("ADD/FAILURE", t.message.toString())
            }
        })
    }

    fun search(userToken: String?, season: ArrayList<String>, kind: ArrayList<String>, bookmark: Boolean) {
        val clothesService = getRetrofit().create(ClothesRetrofitInterface::class.java)
        clothesService.search(userToken, season, kind, bookmark).enqueue(object: Callback<ClothesSearchResponse> {
            override fun onResponse(call: Call<ClothesSearchResponse>, response: Response<ClothesSearchResponse>) {
                Log.d("SEARCH/SUCCESS", response.toString())
                if (response.code() == 400) {
                    searchView.onSearchFailure()
                } else {
                    val resp: ClothesSearchResponse = response.body()!!
                    searchView.onSearchSuccess(resp.clothes)
                }
            }

            override fun onFailure(call: Call<ClothesSearchResponse>, t: Throwable) {
                Log.d("SEARCH/FAILURE", t.message.toString())
            }
        })
    }

    fun bookmark(userToken: String?, clothesIdx: Int, bookmark: Boolean) {
        val clothesService = getRetrofit().create(ClothesRetrofitInterface::class.java)
        clothesService.bookmark(userToken, clothesIdx, bookmark).enqueue(object: Callback<ClothesResponse> {
            override fun onResponse(call: Call<ClothesResponse>, response: Response<ClothesResponse>) {
                Log.d("BOOKMARK/SUCCESS", response.toString())
                if (response.code() == 400) {
                    bookmarkView.onBookmarkFailure()
                } else {
                    bookmarkView.onBookmarkSuccess()
                }
            }

            override fun onFailure(call: Call<ClothesResponse>, t: Throwable) {
                Log.d("BOOKMARK/FAILURE", t.message.toString())
            }
        })
    }

    fun searchInfo(userToken: String?, clothesIdx: Int) {
        val clothesService = getRetrofit().create(ClothesRetrofitInterface::class.java)
        clothesService.searchInfo(userToken, clothesIdx).enqueue(object: Callback<ClothesSearchInfoResponse> {
            override fun onResponse(call: Call<ClothesSearchInfoResponse>, response: Response<ClothesSearchInfoResponse>) {
                Log.d("SEARCHINFO/SUCCESS", response.toString())
                if (response.code() == 400) {
                    detailedInfoView.onSearchInfoFailure()
                } else {
                    val resp: ClothesSearchInfoResponse = response.body()!!
                    detailedInfoView.onSearchInfoSuccess(resp.clothes)
                }
            }

            override fun onFailure(call: Call<ClothesSearchInfoResponse>, t: Throwable) {
                Log.d("SEARCHINFO/FAILURE", t.message.toString())
            }
        })
    }

    fun delete(userToken: String?, clothesIdx: Int) {
        val clothesService = getRetrofit().create(ClothesRetrofitInterface::class.java)
        clothesService.delete(userToken, clothesIdx).enqueue(object: Callback<ClothesResponse> {
            override fun onResponse(call: Call<ClothesResponse>, response: Response<ClothesResponse>) {
                Log.d("DELETE/SUCCESS", response.toString())
                if (response.code() == 400) {
                    detailedInfoView.onDeleteFailure()
                } else {
                    detailedInfoView.onDeleteSuccess()
                }
            }

            override fun onFailure(call: Call<ClothesResponse>, t: Throwable) {
                Log.d("DELETE/FAILURE", t.message.toString())
            }
        })
    }

    fun update(userToken: String?, clothesIdx: Int, clothes: ClothesRequest) {
        val clothesService = getRetrofit().create(ClothesRetrofitInterface::class.java)
        clothesService.update(userToken, clothesIdx, clothes).enqueue(object: Callback<ClothesResponse> {
            override fun onResponse(call: Call<ClothesResponse>, response: Response<ClothesResponse>) {
                Log.d("UPDATE/SUCCESS", response.body()!!.message)
                if (response.code() == 400) {
                    modifyClothesView.onUpdateFailure()
                } else {
                    modifyClothesView.onUpdateSuccess()
                }
            }

            override fun onFailure(call: Call<ClothesResponse>, t: Throwable) {
                Log.d("UPDATE/FAILURE", t.message.toString())
            }
        })
    }
}
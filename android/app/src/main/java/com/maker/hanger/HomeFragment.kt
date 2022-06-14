package com.maker.hanger

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.maker.hanger.adapter.RecommendVPAdapter
import com.maker.hanger.connection.ClothesService
import com.maker.hanger.connection.RecommendView
import com.maker.hanger.connection.WeatherService
import com.maker.hanger.connection.WeatherView
import com.maker.hanger.data.Weather
import com.maker.hanger.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), WeatherView, RecommendView {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private lateinit var recommendVPAdapter: RecommendVPAdapter
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        searchWeather()
        recommendClothes()

        updateUser()
        logout()

        return binding.root
    }

    private fun initViewPager(clothesImageUrl: ArrayList<String>) {
        recommendVPAdapter = RecommendVPAdapter(this)
        for (clothesImage in clothesImageUrl) {
            recommendVPAdapter.addFragment(RecommendFragment(clothesImage))
        }
        binding.homeRecommendVp.adapter = recommendVPAdapter
        binding.homeIndicator.setViewPager2(binding.homeRecommendVp)

        setAutoViewPager()
    }

    private fun setAutoViewPager() {
        handler = Handler(Looper.getMainLooper())
        val autoViewPager = AutoViewPager(recommendVPAdapter)
        autoViewPager.start()
    }

    private fun autoRecommendClothes(recommendAdapter: RecommendVPAdapter) {
        position = (position + 1) % recommendAdapter.itemCount
        binding.homeRecommendVp.setCurrentItem(position, true)
    }

    private fun searchWeather() {
        val weatherService = WeatherService()
        weatherService.setWeatherView(this)
        weatherService.getWeather()
    }

    private fun recommendClothes() {
        val clothesService = ClothesService()
        clothesService.setRecommendView(this)
        clothesService.recommend(getJwt())
    }

    private fun updateUser() {
        binding.homeUserInfoModifyIv.setOnClickListener {
            val intent = Intent(requireContext(), ModifyUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logout() {
        binding.homeLogoutIv.setOnClickListener {
            removeJwt()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }

    private fun getJwt(): String? {
        val sharedPreferences = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences!!.getString("jwt", null)
    }

    private fun removeJwt() {
        val sharedPreferences = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.remove("jwt")
        editor.apply()
    }

    inner class AutoViewPager(private val recommendAdapter: RecommendVPAdapter) : Thread() {
        override fun run() {
            super.run()
            try {
                while (true) {
                    sleep(2500)
                    handler.post {
                        autoRecommendClothes(recommendAdapter)
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Recommend", "Home Recommend Thread is dead.")
            }
        }
    }

    override fun onGetWeatherSuccess(weather: Weather) {
        Log.d("WEATHER/SUCCESS", "날씨 조회를 성공했습니다.")
        with (binding) {
            when (weather.state) {
                "맑음" -> {
                    Glide.with(requireContext()).load(R.raw.weather_sun).into(binding.homeWeatherIv)
                }
                "구름 많음" -> {
                    Glide.with(requireContext()).load(R.raw.weather_cloudy).into(binding.homeWeatherIv)
                }
                else -> {
                    Glide.with(requireContext()).load(R.raw.weather_rain).into(binding.homeWeatherIv)
                }
            }
            homeWeatherPresentTemperatureTv.text = weather.present.toString()
            homeWeatherProbabilityInputTv.text = weather.probability.toString() + "%"
            homeWeatherHumidityInputTv.text = weather.humidity.toString() + "%"
        }
    }

    override fun onGetWeatherFailure() {
        Log.d("WEATHER/FAILURE", "날씨 조회를 실패했습니다.")
    }

    override fun onRecommendSuccess(clothesImageUrl: ArrayList<String>) {
        Log.d("RECOMMEND/SUCCESS", "의류 추천을 성공했습니다.")
        if (clothesImageUrl.size > 0) {
            initViewPager(clothesImageUrl)
        }
    }

    override fun onRecommendFailure() {
        Log.d("RECOMMEND/FAILURE", "의류 추천을 실패했습니다.")
    }
}
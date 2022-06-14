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
import com.maker.hanger.connection.WeatherService
import com.maker.hanger.connection.WeatherView
import com.maker.hanger.data.Weather
import com.maker.hanger.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), WeatherView {
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

        initViewPager()
        updateUser()
        logout()

        handler = Handler(Looper.getMainLooper())
        val autoViewPager = AutoViewPager(recommendVPAdapter)
        autoViewPager.start()

        searchWeather()

        return binding.root
    }

    private fun initViewPager() {
        recommendVPAdapter = RecommendVPAdapter(this)
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        recommendVPAdapter.addFragment(RecommendFragment())
        binding.homeRecommendVp.adapter = recommendVPAdapter
        binding.homeIndicator.setViewPager2(binding.homeRecommendVp)
    }

    private fun recommendClothes(recommendAdapter: RecommendVPAdapter) {
        position = (position + 1) % recommendAdapter.itemCount
        binding.homeRecommendVp.setCurrentItem(position, true)
    }

    private fun searchWeather() {
        val weatherService = WeatherService()
        weatherService.setWeatherView(this)
        weatherService.getWeather()
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
                        recommendClothes(recommendAdapter)
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
}
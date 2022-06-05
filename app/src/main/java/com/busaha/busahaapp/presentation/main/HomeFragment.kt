package com.busaha.busahaapp.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.FragmentHomeBinding
import com.busaha.busahaapp.presentation.business_test.BusinessTestActivity
import com.busaha.busahaapp.presentation.business_trend.BusinessTrendActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingBtn.setOnClickListener(this)
        binding.testBtn.setOnClickListener(this)
        binding.trendBtn.setOnClickListener(this)
        binding.newsBtn.setOnClickListener(this)
        binding.historyBtn.setOnClickListener(this)
        binding.courseBtn.setOnClickListener(this)
        binding.shopBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.setting_btn -> Toast.makeText(context, "Setting btn", Toast.LENGTH_SHORT).show()
            R.id.test_btn -> toBusinessTest()
            R.id.trend_btn -> toBusinessTrend()
            R.id.news_btn -> comingSoon()
            R.id.history_btn -> comingSoon()
            R.id.course_btn -> comingSoon()
            R.id.shop_btn -> comingSoon()
        }
    }

    private fun toBusinessTest() {
        val intent = Intent(context, BusinessTestActivity::class.java)
        startActivity(intent)
    }

    private fun toBusinessTrend() {
        val intent = Intent(context, BusinessTrendActivity::class.java)
        startActivity(intent)
    }

    private fun comingSoon() {
        Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
    }
}
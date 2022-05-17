package com.busaha.busahaapp.presentation.business_result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.busaha.busahaapp.databinding.ActivityBusinessResultBinding
import com.busaha.busahaapp.presentation.main.MainActivity

class BusinessResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeBtn.setOnClickListener { toMain() }
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
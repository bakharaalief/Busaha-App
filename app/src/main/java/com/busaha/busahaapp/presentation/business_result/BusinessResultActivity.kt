package com.busaha.busahaapp.presentation.business_result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.busaha.busahaapp.databinding.ActivityBusinessResultBinding
import com.busaha.busahaapp.ml.Classifier
import com.busaha.busahaapp.presentation.main.MainActivity

class BusinessResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessResultBinding
    private lateinit var classifier: Classifier

    private lateinit var result: List<Pair<String, Float>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClassifier()
        runRecommend()

        binding.homeBtn.setOnClickListener { toMain() }
    }

    private fun setClassifier() {
        classifier = Classifier(assets, "model_busaha.tflite")
    }

    private fun runRecommend() {
        val inputData = intArrayOf(
            100000, 25000000, 1, 2, 10000000, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2
        )

        val labelData = arrayOf(
            "Perternakan",
            "Toserba/Toko Kelontong",
            "Kuliner",
            "Dropshipper",
            "Barang Digital",
            "Elektronik",
            "Pakaian",
            "Kosmetik"
        )

        result = classifier.searchRecommend(inputData, labelData)
        setResultToText()
    }

    private fun setResultToText() {
        binding.result1Text.text = result[0].first
        binding.result1Percent.text = "#1"

        binding.result2Text.text = result[1].first
        binding.result2Percent.text = "#2"

        binding.result3Text.text = result[2].first
        binding.result3Percent.text = "#3"
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
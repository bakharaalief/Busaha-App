package com.busaha.busahaapp.presentation.business_result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.databinding.ActivityBusinessResultBinding
import com.busaha.busahaapp.ml.Classifier
import com.busaha.busahaapp.presentation.ViewModelFactory
import com.busaha.busahaapp.presentation.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class BusinessResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessResultBinding
    private lateinit var viewModel: BusinessResultViewModel
    private lateinit var classifier: Classifier
    private lateinit var inputData: IntArray
    private lateinit var labelData: Array<String>
    private lateinit var result: List<Pair<String, Float>>

    private val modelTFLite = "model_busaha_new.tflite"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClassifier()
        setLabelAndInputData()
        setViewModel()

        binding.homeBtn.setOnClickListener { toMain() }
    }

    private fun setClassifier() {
        classifier = Classifier(assets, modelTFLite)
    }

    private fun setLabelAndInputData() {
        inputData = IntArray(16)
        labelData = arrayOf(
            "Perternakan",
            "Toserba/Toko Kelontong",
            "Kuliner",
            "Dropshipper",
            "Barang Digital",
            "Elektronik",
            "Pakaian",
            "Kosmetik"
        )
    }

    private fun runRecommend() {
        result = classifier.searchRecommend(inputData, labelData)
        setResultToText()
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(this, dataStore)
        viewModel = ViewModelProvider(viewModelStore, factory)[BusinessResultViewModel::class.java]

        viewModel.getAllAnswerOption.observe(this) {
            for (i in it.indices) {
                inputData[i] = it[i].index ?: 0
            }

            runRecommend()
        }
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
        viewModel.deleteAllAnswer()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        viewModel.deleteAllAnswer()
        super.onBackPressed()
    }
}
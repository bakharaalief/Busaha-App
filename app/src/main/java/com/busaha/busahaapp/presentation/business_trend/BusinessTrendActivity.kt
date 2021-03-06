package com.busaha.busahaapp.presentation.business_trend

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.busaha.busahaapp.databinding.ActivityBusinessTrendBinding
import com.busaha.busahaapp.presentation.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class BusinessTrendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessTrendBinding
    private lateinit var viewModel: BusinessTrendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessTrendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        setViewModel()
        setRv()
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> true
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(this, dataStore)
        viewModel = ViewModelProvider(this, factory)[BusinessTrendViewModel::class.java]
    }

    private fun setRv() {
        val adapter = TrendListAdapter()

        binding.trendRv.layoutManager = LinearLayoutManager(this)
        binding.trendRv.adapter = adapter

        viewModel.listTrend.observe(this) {
            adapter.submitList(it)
        }
    }
}
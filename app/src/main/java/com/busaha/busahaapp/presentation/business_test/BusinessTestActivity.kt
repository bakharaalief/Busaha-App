package com.busaha.busahaapp.presentation.business_test

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.ActivityBusinessTestBinding

class BusinessTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        setFragment()
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

    private fun setFragment() {
        val mFragmentManager = supportFragmentManager
        val mTestFragment = TestFragment()

        mFragmentManager
            .beginTransaction()
            .add(
                R.id.test_fragment_container,
                mTestFragment,
            )
            .commit()
    }
}
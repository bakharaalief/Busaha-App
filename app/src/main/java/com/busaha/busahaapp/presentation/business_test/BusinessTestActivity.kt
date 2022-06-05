package com.busaha.busahaapp.presentation.business_test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.ActivityBusinessTestBinding
import com.busaha.busahaapp.presentation.ViewModelFactory
import com.busaha.busahaapp.presentation.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class BusinessTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessTestBinding
    private lateinit var viewModel: TestViewModel

    private var countAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessTestBinding.inflate(layoutInflater)
        setViewModel()
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

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(this, dataStore)
        viewModel = ViewModelProvider(this, factory)[TestViewModel::class.java]

        viewModel.getCountAnswer().observe(this) {
            countAnswer = it
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

    override fun onBackPressed() {
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setMessage(if (countAnswer == 0) "Apa kamu yakin mau keluar test ?" else "Apa kamu yakin mau keluar test ? Semua jawaban akan hilang")
                setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    if (countAnswer != 0) viewModel.deleteAllAnswer()
                    toMain()
                }
                setNegativeButton(
                    "No"
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            }

            // Create the AlertDialog
            builder.create()
        }
        alertDialog.show()
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
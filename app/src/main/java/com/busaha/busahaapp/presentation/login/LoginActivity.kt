package com.busaha.busahaapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.busaha.busahaapp.databinding.ActivityLoginBinding
import com.busaha.busahaapp.presentation.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        binding.loginBtn.setOnClickListener {
            toMain()
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.materialToolbar)
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

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
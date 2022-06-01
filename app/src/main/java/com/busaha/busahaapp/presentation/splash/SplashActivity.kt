package com.busaha.busahaapp.presentation.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.R
import com.busaha.busahaapp.domain.entity.UserLogin
import com.busaha.busahaapp.presentation.ViewModelFactory
import com.busaha.busahaapp.presentation.main.MainActivity
import com.busaha.busahaapp.presentation.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var user: UserLogin

    private var isLogin = false
    private val splashTime = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setViewModel()

        Handler(Looper.getMainLooper()).postDelayed({
            if (isLogin) toMain() else toWelcome()
        }, splashTime)
    }

    private fun toWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(this, dataStore)
        viewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]
        viewModel.getUserData().observe(this) {
            isLogin = it.isLogin
            user = UserLogin(it.localId, it.displayName)
        }
    }
}
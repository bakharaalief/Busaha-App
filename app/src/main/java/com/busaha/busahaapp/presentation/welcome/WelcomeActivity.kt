package com.busaha.busahaapp.presentation.welcome

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.ActivityWelcomeBinding
import com.busaha.busahaapp.presentation.login.LoginActivity
import com.busaha.busahaapp.presentation.main.MainActivity
import com.busaha.busahaapp.presentation.register.RegisterActivity

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityWelcomeBinding

    private val animationDuration = 4000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //play animation for image in welcome
        playAnimation()

        binding.loginBtn.setOnClickListener(this)
        binding.registerBtn.setOnClickListener(this)
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.welcomeImage, View.TRANSLATION_Y, -30f, 30f).apply {
            duration = animationDuration
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.login_btn -> toLogin()
            R.id.register_btn -> toRegister()
        }
    }

    private fun toLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun toRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
package com.busaha.busahaapp.presentation.welcome

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.ActivityWelcomeBinding
import com.busaha.busahaapp.presentation.login.LoginActivity

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityWelcomeBinding

    private val animationDuration = 4000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //play animation for image in welcome
        playAnimation()

        //binding button
        binding.loginBtn.setOnClickListener(this)
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
        }
    }

    private fun toLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
package com.example.todoapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.util.Constants.Companion.SPLASH_TIME_OUT

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadAnimation()
        launchHomeScreen()
    }

    /**
     * Method to start and load animation
     */
    private fun loadAnimation() {
        val splashText = findViewById<TextView>(R.id.activity_splash_textView)
        val animation = AnimationUtils.loadAnimation(this, R.anim.logo_anim)
        splashText.startAnimation(animation)
    }

    /**
     * Method to launch home screen
     */
    private fun launchHomeScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
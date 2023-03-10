package com.seda.filmsearchapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.seda.filmsearchapp.MainActivity
import com.seda.filmsearchapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val timer =   object : CountDownTimer(2300, 3000) {
            override fun onFinish() {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)

            }
            override fun onTick(p0: Long) {
                Log.d("SplashActivity", p0.toString())
            }
        }
        timer.start()
    }
    }

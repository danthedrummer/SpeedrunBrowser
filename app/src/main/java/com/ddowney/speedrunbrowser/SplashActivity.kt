package com.ddowney.speedrunbrowser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, MainActivity::class.java)

        val runnable  = Runnable {
            startActivity(intent)
            finish()
        }
        val handler = Handler()
        handler.postDelayed(runnable, 5000)

        splash_image.setOnClickListener {
            handler.removeCallbacks(runnable)
            startActivity(intent)
            finish()
        }

    }
}

package com.ddowney.speedrunbrowser

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ddowney.speedrunbrowser.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySplashBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySplashBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val intent = Intent(this, MainActivity::class.java)

    val runnable = Runnable {
      startActivity(intent)
      finish()
    }
    val handler = Handler()
    handler.postDelayed(runnable, 5000)

    binding.splashImage.setOnClickListener {
      handler.removeCallbacks(runnable)
      startActivity(intent)
      finish()
    }

  }
}

package com.example.spotifycustom.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spotifycustom.MainActivity
import com.example.spotifycustom.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SplashScreenActivity:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        runBlocking {
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            delay(SPLASH_DELAY)
            startActivity(intent)
            finish()
        }
    }



    companion object {
        private const val SPLASH_DELAY = 2000L
    }
}
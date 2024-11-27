package com.example.novelmanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class startActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("com.example.novelmanager_preferences", MODE_PRIVATE)
        applyUserSettings()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Simular una carga de datos o un retardo
        val splashScreenDuration = 2000L // 2 segundos
        window.decorView.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashScreenDuration)
    }

    private fun applyUserSettings() {
        val darkMode = sharedPreferences.getBoolean("dark_mode", false)
        if (darkMode) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

}
package com.example.novelmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class startActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Simular una carga de datos o un retardo
        val splashScreenDuration = 2000L // 2 segundos
        window.decorView.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashScreenDuration)
    }
}
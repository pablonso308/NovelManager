package com.example.novelmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.novelmanager.database.entidades.Novel

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.net.conn.CONNECTIVITY_CHANGE") {
            if (isWifiConnected(context)) {
                // Obtener los datos del Intent
                val title = intent.getStringExtra("title") ?: "Default Title"
                val author = intent.getStringExtra("author") ?: "Default Author"
                val year = intent.getIntExtra("year", 2023)
                val synopsis = intent.getStringExtra("synopsis") ?: "Default Synopsis"

                // Crear la novela con los datos del usuario
                val novelToSend = Novel(
                    id = 0,
                    title = title,
                    author = author,
                    year = year,
                    synopsis = synopsis
                )
                val syncDataTask = SyncDataTask(context, novelToSend)
                syncDataTask.execute()
            }
        }
    }

    private fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.type == ConnectivityManager.TYPE_WIFI
        }
    }
}
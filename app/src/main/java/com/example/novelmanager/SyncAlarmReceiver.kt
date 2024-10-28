package com.example.novelmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.novelmanager.database.entidades.Novel

class SyncAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Start the SyncDataTask when the alarm is received
        val novelToSend = Novel(
            id = 0,
            title = "Sample Title",
            author = "Sample Author",
            year = 2023,
            synopsis = "Sample Synopsis"
        )
        val syncDataTask = SyncDataTask(context, novelToSend)
        syncDataTask.execute()
    }
}
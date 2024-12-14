package com.example.novelmanager

import android.app.Application
import androidx.room.Room
import com.example.novelmanager.database.NovelDatabase
import com.example.novelmanager.database.NovelDatabase.Companion.MIGRATION_1_2
import com.example.novelmanager.database.NovelDatabase.Companion.MIGRATION_2_3

class NovelManagerApplication : Application() {
    val database: NovelDatabase by lazy {
        Room.databaseBuilder(
            this,
            NovelDatabase::class.java,
            "novel_database"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
    }
}
package com.example.novelmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.novelmanager.novelaDatabase.NovelDao
import com.example.novelmanager.database.entidades.Novel

@Database(entities = [Novel::class], version = 3)
abstract class NovelDatabase : RoomDatabase() {
    abstract fun novelDao(): NovelDao

    companion object {
        @Volatile
        private var INSTANCE: NovelDatabase? = null

        fun getInstance(context: Context): NovelDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NovelDatabase::class.java,
                    "novel_database"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
                INSTANCE = instance
                instance
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migration logic from version 1 to 2
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add columns latitude, longitude, and isFavorite if they do not exist
                val cursor = database.query("PRAGMA table_info(novel_table)")
                var latitudeColumnExists = false
                var longitudeColumnExists = false
                var isFavoriteColumnExists = false
                while (cursor.moveToNext()) {
                    val columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    if (columnName == "latitude") {
                        latitudeColumnExists = true
                    }
                    if (columnName == "longitude") {
                        longitudeColumnExists = true
                    }
                    if (columnName == "isFavorite") {
                        isFavoriteColumnExists = true
                    }
                }
                cursor.close()

                if (!latitudeColumnExists) {
                    database.execSQL("ALTER TABLE novel_table ADD COLUMN latitude REAL")
                }
                if (!longitudeColumnExists) {
                    database.execSQL("ALTER TABLE novel_table ADD COLUMN longitude REAL")
                }
                if (!isFavoriteColumnExists) {
                    database.execSQL("ALTER TABLE novel_table ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
                }
            }
        }
    }
}
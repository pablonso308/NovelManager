package com.example.novelmanager.SQLlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createNovelsTable = """
            CREATE TABLE novels (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                year INTEGER NOT NULL,
                synopsis TEXT NOT NULL,
                isFavorite INTEGER NOT NULL DEFAULT 0,
                latitude REAL,
                longitude REAL
            )
        """.trimIndent()
        db.execSQL(createNovelsTable)

        val createReviewsTable = """
            CREATE TABLE reviews (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                novelId INTEGER NOT NULL,
                reviewer TEXT NOT NULL,
                rating INTEGER NOT NULL,
                comment TEXT NOT NULL,
                FOREIGN KEY(novelId) REFERENCES novels(id)
            )
        """.trimIndent()
        db.execSQL(createReviewsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE novels ADD COLUMN latitude REAL")
            db.execSQL("ALTER TABLE novels ADD COLUMN longitude REAL")
        }
        if (oldVersion < 3) {
            // Add any additional changes for version 3 here
        }
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Manejar la degradación eliminando y recreando las tablas
        db.execSQL("DROP TABLE IF EXISTS reviews")
        db.execSQL("DROP TABLE IF EXISTS novels")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "novelmanager.db"
        private const val DATABASE_VERSION = 3 // Asegúrate de usar la versión correcta de tu esquema
    }
}

package com.example.novelmanager.SQLlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.novelmanager.database.entidades.Novel

class SQLDao(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    private val database: SQLiteDatabase = dbHelper.readableDatabase

    fun insertNovel(
        title: String,
        author: String,
        year: Int,
        synopsis: String,
        isFavorite: Boolean,
        latitude: Double?,
        longitude: Double?
    ): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("author", author)
            put("year", year)
            put("synopsis", synopsis)
            put("isFavorite", if (isFavorite) 1 else 0)
            put("latitude", latitude)
            put("longitude", longitude)
        }
        val result = db.insert("novels", null, values)
        db.close()
        return result
    }

    fun getAllNovels(): Cursor {
        val db = dbHelper.readableDatabase
        return db.query("novels", null, null, null, null, null, null)
    }

    fun deleteNovel(id: Long): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete("novels", "id = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun updateNovel(novel: Novel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", novel.title)
            put("author", novel.author)
            put("year", novel.year)
            put("synopsis", novel.synopsis)
            put("isFavorite", if (novel.isFavorite) 1 else 0)
            put("latitude", novel.latitude)
            put("longitude", novel.longitude)
        }
        val result = db.update("novels", values, "id = ?", arrayOf(novel.id.toString()))
        db.close()
        return result
    }

    fun insertReview(novelId: Int, reviewer: String, rating: Int, comment: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("novelId", novelId)
            put("reviewer", reviewer)
            put("rating", rating)
            put("comment", comment)
        }
        val result = db.insert("reviews", null, values)
        db.close()
        return result
    }

    fun getReviewsForNovel(novelId: Int): Cursor {
        val db = dbHelper.readableDatabase
        return db.query("reviews", null, "novelId = ?", arrayOf(novelId.toString()), null, null, null)
    }

    fun getFavoriteNovels(): Cursor {
        val query = "SELECT * FROM novels WHERE isFavorite = 1"
        return database.rawQuery(query, null)
    }
}
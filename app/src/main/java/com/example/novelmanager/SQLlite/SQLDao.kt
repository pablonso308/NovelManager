package com.example.novelmanager.SQLlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.novelmanager.database.entidades.Novel

class SQLDao(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // Novel operations
    fun insertNovel(title: String, author: String, year: Int, synopsis: String, isFavorite: Boolean): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("author", author)
            put("year", year)
            put("synopsis", synopsis)
            put("isFavorite", if (isFavorite) 1 else 0)
        }
        return db.insert("novels", null, values)
    }

    fun getAllNovels(): Cursor {
        val db = dbHelper.readableDatabase
        return db.query("novels", null, null, null, null, null, null)
    }

    fun deleteNovel(id: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete("novels", "id = ?", arrayOf(id.toString()))
    }

    fun updateNovel(novel: Novel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", novel.title)
            put("author", novel.author)
            put("year", novel.year)
            put("synopsis", novel.synopsis)
            put("isFavorite", if (novel.isFavorite) 1 else 0)
        }
        return db.update("novels", values, "id = ?", arrayOf(novel.id.toString()))
    }

    // Review operations
    fun insertReview(novelId: Int, reviewer: String, rating: Int, comment: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("novelId", novelId)
            put("reviewer", reviewer)
            put("rating", rating)
            put("comment", comment)
        }
        return db.insert("reviews", null, values)
    }

    fun getReviewsForNovel(novelId: Int): Cursor {
        val db = dbHelper.readableDatabase
        return db.query("reviews", null, "novelId = ?", arrayOf(novelId.toString()), null, null, null)
    }
}
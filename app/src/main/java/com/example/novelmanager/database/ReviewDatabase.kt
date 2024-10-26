package com.example.novelmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.novelmanager.database.dao.ReviewDao
import com.example.novelmanager.database.entidades.Review

@Database(entities = [Review::class], version = 1)
abstract class ReviewDatabase : RoomDatabase() {

    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var instance: ReviewDatabase? = null

        fun getInstance(context: Context): ReviewDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ReviewDatabase::class.java, "review_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }
}
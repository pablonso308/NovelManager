package com.example.novelmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.novelmanager.database.entidades.Review

@Dao
interface ReviewDao {
    @Insert
    suspend fun insert(review: Review)

    @Query("SELECT * FROM reviews WHERE novelId = :novelId")
    fun getReviewsForNovel(novelId: Int): LiveData<List<Review>>
}
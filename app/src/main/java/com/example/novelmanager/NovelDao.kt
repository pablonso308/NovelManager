package com.example.novelmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.novelmanager.database.entidades.Novel

@Dao
interface NovelDao {
    @Insert
    suspend fun insert(novel: Novel)

    @Delete
    suspend fun delete(novel: Novel)

    @Query("SELECT * FROM novel_table ORDER BY title ASC")
    fun getAllNovels(): LiveData<List<Novel>>

    @Query("UPDATE novel_table SET isFavorite = :isFavorite WHERE id = :novelId")
    suspend fun updateFavoriteStatus(novelId: Int, isFavorite: Boolean)
}
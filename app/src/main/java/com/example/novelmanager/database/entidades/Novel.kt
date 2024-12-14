package com.example.novelmanager.database.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "novel_table")
data class Novel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val year: Int,
    val synopsis: String,
    val isFavorite: Boolean ,
    val latitude: Double? ,
    val longitude: Double?
)
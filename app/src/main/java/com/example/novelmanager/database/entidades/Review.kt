package com.example.novelmanager.database.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val novelId: Int,
    val reviewer: String,
    val rating: Int,
    val comment: String
)
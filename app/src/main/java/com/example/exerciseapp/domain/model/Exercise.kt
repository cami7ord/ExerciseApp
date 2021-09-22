package com.example.exerciseapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
data class Exercise(
    @PrimaryKey
    val id: Long,
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean = false,
)

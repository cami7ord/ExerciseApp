package com.example.exerciseapp.domain.repository

import com.example.exerciseapp.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExercises(): Flow<List<Exercise>>
    suspend fun getExerciseById(id: Long): Exercise
    suspend fun setFavoriteExercise(id: Long, isFavorite: Boolean)
}

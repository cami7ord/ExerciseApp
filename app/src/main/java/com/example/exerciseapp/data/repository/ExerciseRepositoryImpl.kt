package com.example.exerciseapp.data.repository

import com.example.exerciseapp.domain.model.Exercise
import com.example.exerciseapp.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class ExerciseRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
): ExerciseRepository {

    override suspend fun getExercises(): Flow<List<Exercise>> {
        if (localDataSource.isEmpty()) {
            val exercises = remoteDataSource.getExercises()
            localDataSource.saveExercises(exercises)
        }
        return localDataSource.getExercises()
    }

    override suspend fun getExerciseById(id: Long): Exercise {
        return localDataSource.getById(id)
    }

    override suspend fun setFavoriteExercise(id: Long, isFavorite: Boolean) {
        localDataSource.setFavoriteExercise(
            exerciseId = id,
            isFavorite = isFavorite
        )
    }
}

interface RemoteDataSource {
    suspend fun getExercises(): List<Exercise>
}

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveExercises(exercises: List<Exercise>)
    suspend fun setFavoriteExercise(exerciseId: Long, isFavorite: Boolean)
    suspend fun getById(exerciseId: Long): Exercise
    fun getExercises(): Flow<List<Exercise>>
}

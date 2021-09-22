package com.example.exerciseapp.data.local

import com.example.exerciseapp.data.local.dao.ExerciseDao
import com.example.exerciseapp.data.repository.LocalDataSource
import com.example.exerciseapp.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val exerciseDao: ExerciseDao,
) : LocalDataSource {

    override suspend fun isEmpty(): Boolean = exerciseDao.count() <= 0

    override suspend fun saveExercises(exercises: List<Exercise>) {
        exerciseDao.insertExercises(exercises)
    }

    override suspend fun setFavoriteExercise(exerciseId: Long, isFavorite: Boolean) {
        exerciseDao.setFavorite(id = exerciseId, isFavorite = isFavorite)
    }

    override suspend fun getById(exerciseId: Long): Exercise {
        return exerciseDao.getById(exerciseId)
    }

    override fun getExercises(): Flow<List<Exercise>> = exerciseDao.getAll()

}

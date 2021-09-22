package com.example.exerciseapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.exerciseapp.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise_table ORDER BY id ASC")
    fun getAll(): Flow<List<Exercise>>

    @Query("SELECT COUNT(id) FROM exercise_table")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercises(exercises: List<Exercise>)

    @Query("UPDATE exercise_table SET isFavorite = :isFavorite WHERE id =:id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean)

    @Query("SELECT * FROM exercise_table WHERE id =:id LIMIT 1")
    suspend fun getById(id: Long) : Exercise

}

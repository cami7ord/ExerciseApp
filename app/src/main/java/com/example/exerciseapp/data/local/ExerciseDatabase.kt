package com.example.exerciseapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exerciseapp.data.local.dao.ExerciseDao
import com.example.exerciseapp.domain.model.Exercise

@Database(entities = [Exercise::class], version = 1, exportSchema = false)
abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun exerciseDao() : ExerciseDao
}

package com.example.exerciseapp.di

import android.app.Application
import androidx.room.Room
import com.example.exerciseapp.data.local.ExerciseDatabase
import com.example.exerciseapp.data.local.dao.ExerciseDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideExerciseDao(get()) }
}

fun provideDatabase(application: Application): ExerciseDatabase {
    return Room.databaseBuilder(application, ExerciseDatabase::class.java, "exercises")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideExerciseDao(database: ExerciseDatabase): ExerciseDao {
    return  database.exerciseDao()
}

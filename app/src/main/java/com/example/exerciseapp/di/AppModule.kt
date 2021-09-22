package com.example.exerciseapp.di

import com.example.exerciseapp.data.local.LocalDataSourceImpl
import com.example.exerciseapp.data.network.RemoteDataSourceImpl
import com.example.exerciseapp.data.repository.ExerciseRepositoryImpl
import com.example.exerciseapp.data.repository.LocalDataSource
import com.example.exerciseapp.data.repository.RemoteDataSource
import com.example.exerciseapp.domain.repository.ExerciseRepository
import com.example.exerciseapp.ui.viewmodel.ExerciseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<LocalDataSource> {
        LocalDataSourceImpl(
            exerciseDao = get()
        )
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(
            apiService = get()
        )
    }

    single<ExerciseRepository> {
        ExerciseRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get(),
        )
    }

    viewModel {
        ExerciseViewModel(
            repository = get()
        )
    }
}

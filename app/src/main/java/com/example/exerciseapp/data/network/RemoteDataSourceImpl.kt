package com.example.exerciseapp.data.network

import com.example.exerciseapp.data.network.dto.toModel
import com.example.exerciseapp.data.repository.RemoteDataSource
import com.example.exerciseapp.domain.model.Exercise

class RemoteDataSourceImpl(
    private val apiService: ApiService,
): RemoteDataSource {
    override suspend fun getExercises(): List<Exercise> {
        return apiService.getExercises().map { dto ->
            dto.toModel()
        }
    }
}

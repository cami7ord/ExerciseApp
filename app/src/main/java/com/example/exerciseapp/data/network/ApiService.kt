package com.example.exerciseapp.data.network

import com.example.exerciseapp.data.network.dto.ExerciseDto
import retrofit2.http.GET

interface ApiService {
    @GET("jsonBlob/027787de-c76e-11eb-ae0a-39a1b8479ec2")
    suspend fun getExercises(): List<ExerciseDto>
}
